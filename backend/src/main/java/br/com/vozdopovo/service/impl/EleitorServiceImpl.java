package br.com.vozdopovo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.repository.EleitorRepository;
import br.com.vozdopovo.service.EleitorService;
import br.com.vozdopovo.exception.eleitor.EleitorEmailDuplicadoException;
import br.com.vozdopovo.exception.eleitor.EleitorJaDesativadoException;
import br.com.vozdopovo.exception.eleitor.EleitorNotFoundException;
import br.com.vozdopovo.exception.validation.CampoObrigatorioException;
import br.com.vozdopovo.exception.validation.FormatoInvalidoException;
@Service
public class EleitorServiceImpl implements EleitorService {

    private final EleitorRepository eleitorRepository;

    public EleitorServiceImpl(EleitorRepository eleitorRepository) {
        this.eleitorRepository = eleitorRepository;
    }

    // Retorna o cadastramento da conta de um novo eleitor
    @Transactional 
    @Override
    public Eleitor criar(Eleitor eleitor) {
        normalizarCampos(eleitor);
        validarCamposObrigatorios(eleitor);
        validarFormatoEmail(eleitor.getEmail());

        if (eleitorRepository.findByEmail(eleitor.getEmail()).isPresent()) {
            throw new EleitorEmailDuplicadoException(eleitor.getEmail());
        }

        eleitor.setStatus(StatusConta.ATIVA);

        return eleitorRepository.save(eleitor);
    }

    // Retorna a busca de um eleitor pelo Id
    @Transactional (readOnly = true)
    @Override
    public Eleitor buscarPorId(Long id) {
        return eleitorRepository.findById(id)
                .orElseThrow(() -> new EleitorNotFoundException(id));
    }

    // Retorna a lista de todos os eleitor em que a conta tem status ATIVA
    @Transactional (readOnly = true)
    @Override
    public List<Eleitor> listarTodosAtivos() {
        return eleitorRepository.findByStatus(StatusConta.ATIVA);
    }

    // Retorna a atualização dos dados  
    @Transactional 
    @Override
    public Eleitor atualizarDados(Long id, Eleitor eleitorAtualizado) {
        Eleitor eleitorExistente = buscarPorId(id);

        atualizarCampoNome(eleitorExistente, eleitorAtualizado);
        atualizarCampoEmail(eleitorExistente, eleitorAtualizado);
        atualizarCampoSenha(eleitorExistente, eleitorAtualizado);

        return eleitorRepository.save(eleitorExistente);
    }

    // Torna a conta com status DESATIVADA
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

    // Verificação do preenchimento dos campos
    private void normalizarCampos(Eleitor eleitor) {
        if (eleitor.getNome() != null) {
            eleitor.setNome(eleitor.getNome().trim());
        }

        if (eleitor.getEmail() != null) {
            eleitor.setEmail(eleitor.getEmail().trim());
        }
    }

    // Validação de campos que devem ter preenchimento obrigatório
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

    // Validação do formato do email eleitor
    private void validarFormatoEmail(String email) {
        if (!email.contains("@")) {
            throw new FormatoInvalidoException("email", "deve conter @");
        }
    }

    // Atualização, com verificação, do nome do eleitor
    private void atualizarCampoNome(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getNome() != null) {
            String nome = atualizado.getNome().trim();

            if (nome.isBlank()) {
                throw new CampoObrigatorioException("nome");
            }

            existente.setNome(nome);
        }
    }

    // Atualização, com verificação, do email do eleitor
    private void atualizarCampoEmail(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getEmail() != null) {
            String email = atualizado.getEmail().trim();

            if (email.isBlank()) {
                throw new CampoObrigatorioException("email");
            }

            validarFormatoEmail(email);

            if (!existente.getEmail().equals(email)
                    && eleitorRepository.findByEmail(email).isPresent()) {
                throw new EleitorEmailDuplicadoException(existente.getEmail());
            }

            existente.setEmail(email);
        }
    }

    // Atualização, com verificação, da senha do eleitor
    private void atualizarCampoSenha(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getSenha() != null) {
            String senha = atualizado.getSenha().trim();

            if (senha.isBlank()) {
                throw new CampoObrigatorioException("senha");
            }

            existente.setSenha(senha);
        }
    }
}
