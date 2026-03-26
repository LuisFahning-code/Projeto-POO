package br.com.vozdopovo.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.exception.eleitor.EleitorEmailDuplicadoException;
import br.com.vozdopovo.exception.eleitor.EleitorJaDesativadoException;
import br.com.vozdopovo.exception.eleitor.EleitorNotFoundException;
import br.com.vozdopovo.exception.validation.CampoObrigatorioException;
import br.com.vozdopovo.exception.validation.FormatoInvalidoException;
import br.com.vozdopovo.repository.EleitorRepository;
import br.com.vozdopovo.service.EleitorService;

@Service
public class EleitorServiceImpl implements EleitorService {

    private final EleitorRepository eleitorRepository;
    // CORREÇÃO #1: injetar PasswordEncoder para hashear senhas
    private final PasswordEncoder passwordEncoder;

    public EleitorServiceImpl(EleitorRepository eleitorRepository,
                              PasswordEncoder passwordEncoder) {
        this.eleitorRepository = eleitorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Eleitor criar(Eleitor eleitor) {
        normalizarCampos(eleitor);
        validarCamposObrigatorios(eleitor);
        validarFormatoEmail(eleitor.getEmail());

        if (eleitorRepository.findByEmail(eleitor.getEmail()).isPresent()) {
            throw new EleitorEmailDuplicadoException(eleitor.getEmail());
        }

        // CORREÇÃO #1: hashear senha antes de persistir
        eleitor.setSenha(passwordEncoder.encode(eleitor.getSenha()));
        eleitor.setStatus(StatusConta.ATIVA);

        return eleitorRepository.save(eleitor);
    }

    @Transactional(readOnly = true)
    @Override
    public Eleitor buscarPorId(Long id) {
        return eleitorRepository.findById(id)
                .orElseThrow(() -> new EleitorNotFoundException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Eleitor> listarTodosAtivos() {
        return eleitorRepository.findByStatus(StatusConta.ATIVA);
    }

    @Transactional
    @Override
    public Eleitor atualizarDados(Long id, Eleitor eleitorAtualizado) {
        Eleitor eleitorExistente = buscarPorId(id);

        atualizarCampoNome(eleitorExistente, eleitorAtualizado);
        atualizarCampoEmail(eleitorExistente, eleitorAtualizado);
        atualizarCampoSenha(eleitorExistente, eleitorAtualizado);

        return eleitorRepository.save(eleitorExistente);
    }

    @Transactional
    @Override
    public void desativar(Long id) {
        Eleitor eleitor = buscarPorId(id);

        if (eleitor.getStatus() == StatusConta.DESATIVADA) {
            throw new EleitorJaDesativadoException(id);
        }

        eleitor.setStatus(StatusConta.DESATIVADA);
        eleitorRepository.save(eleitor);
    }

    // =============================
    //  MÉTODOS AUXILIARES
    // =============================

    private void normalizarCampos(Eleitor eleitor) {
        if (eleitor.getNome() != null) {
            eleitor.setNome(eleitor.getNome().trim());
        }
        if (eleitor.getEmail() != null) {
            eleitor.setEmail(eleitor.getEmail().trim());
        }
    }

    private void validarCamposObrigatorios(Eleitor eleitor) {
        if (eleitor.getNome() == null || eleitor.getNome().isBlank()) {
            throw new CampoObrigatorioException("nome");
        }
        if (eleitor.getEmail() == null || eleitor.getEmail().isBlank()) {
            throw new CampoObrigatorioException("email");
        }
        if (eleitor.getSenha() == null || eleitor.getSenha().isBlank()) {
            throw new CampoObrigatorioException("senha");
        }
    }

    private void validarFormatoEmail(String email) {
        if (!email.contains("@")) {
            throw new FormatoInvalidoException("email", "deve conter @");
        }
    }

    private void atualizarCampoNome(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getNome() != null) {
            String nome = atualizado.getNome().trim();
            if (nome.isBlank()) {
                throw new CampoObrigatorioException("nome");
            }
            existente.setNome(nome);
        }
    }

    private void atualizarCampoEmail(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getEmail() != null) {
            String email = atualizado.getEmail().trim();
            if (email.isBlank()) {
                throw new CampoObrigatorioException("email");
            }
            validarFormatoEmail(email);
            if (!existente.getEmail().equals(email)
                    && eleitorRepository.findByEmail(email).isPresent()) {
                // CORREÇÃO #2: passar o novo email (variável local), não o email antigo
                throw new EleitorEmailDuplicadoException(email);
            }
            existente.setEmail(email);
        }
    }

    private void atualizarCampoSenha(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getSenha() != null) {
            String senha = atualizado.getSenha().trim();
            if (senha.isBlank()) {
                throw new CampoObrigatorioException("senha");
            }
            // CORREÇÃO #1: hashear a nova senha antes de atualizar
            existente.setSenha(passwordEncoder.encode(senha));
        }
    }
}