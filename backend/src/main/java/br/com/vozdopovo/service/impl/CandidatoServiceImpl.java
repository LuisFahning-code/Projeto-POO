package br.com.vozdopovo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.exception.candidato.CandidatoEmailDuplicadoException;
import br.com.vozdopovo.exception.candidato.CandidatoJaDesativadoException;
import br.com.vozdopovo.exception.candidato.CandidatoNotFoundException;
import br.com.vozdopovo.repository.CandidatoRepository;
import br.com.vozdopovo.service.CandidatoService;

@Service
public class CandidatoServiceImpl implements CandidatoService {

    private final CandidatoRepository candidatoRepository;

    public CandidatoServiceImpl(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    @Override
    public Candidato criar(Candidato candidato) {
        normalizarCampos(candidato);
        validarCamposObrigatorios(candidato);
        validarFormatoEmail(candidato.getEmail());

        if (candidatoRepository.findByEmail(candidato.getEmail()).isPresent()) {
            throw new CandidatoEmailDuplicadoException(candidato.getEmail()); // <-- antes: RuntimeException
        }

        candidato.setStatus(StatusConta.ATIVA);
        return candidatoRepository.save(candidato);
    }

    @Override
    public Candidato buscarPorId(Long id) {
        return candidatoRepository.findById(id)
                .orElseThrow(() -> new CandidatoNotFoundException(id)); // <-- antes: RuntimeException
    }

    @Override
    public List<Candidato> listarTodosAtivos() {
        return candidatoRepository.findByStatus(StatusConta.ATIVA);
    }

    @Override
    public List<Candidato> buscarPorNome(String nome) {
        return candidatoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public Candidato atualizarDados(Long id, Candidato candidatoAtualizado) {
        Candidato candidatoExistente = buscarPorId(id);

        atualizarCampoNome(candidatoExistente, candidatoAtualizado);
        atualizarCampoEmail(candidatoExistente, candidatoAtualizado);
        atualizarCampoSenha(candidatoExistente, candidatoAtualizado);
        atualizarCampoPartido(candidatoExistente, candidatoAtualizado);
        atualizarCampoCargo(candidatoExistente, candidatoAtualizado);
        atualizarCampoBiografia(candidatoExistente, candidatoAtualizado);

        return candidatoRepository.save(candidatoExistente);
    }

    @Override
    public void desativar(Long id) {
        Candidato candidato = buscarPorId(id);

        if (candidato.getStatus() == StatusConta.DESATIVADA) {
            throw new CandidatoJaDesativadoException(id); // <-- antes: RuntimeException
        }

        candidato.setStatus(StatusConta.DESATIVADA);
        candidatoRepository.save(candidato);
    }

    // =============================
    //  MÉTODOS AUXILIARES (sem alteração)
    // =============================

    private void normalizarCampos(Candidato candidato) {
        if (candidato.getNome() != null) candidato.setNome(candidato.getNome().trim());
        if (candidato.getEmail() != null) candidato.setEmail(candidato.getEmail().trim());
        if (candidato.getPartido() != null) candidato.setPartido(candidato.getPartido().trim());
        if (candidato.getCargo() != null) candidato.setCargo(candidato.getCargo().trim());
        if (candidato.getBiografia() != null) candidato.setBiografia(candidato.getBiografia().trim());
    }

    private void validarCamposObrigatorios(Candidato candidato) {
        if (candidato.getNome() == null || candidato.getNome().isBlank())
            throw new RuntimeException("Nome é obrigatório");
        if (candidato.getEmail() == null || candidato.getEmail().isBlank())
            throw new RuntimeException("E-mail é obrigatório");
        if (candidato.getSenha() == null || candidato.getSenha().isBlank())
            throw new RuntimeException("Senha é obrigatória");
        if (candidato.getPartido() == null || candidato.getPartido().isBlank())
            throw new RuntimeException("Partido é obrigatório");
        if (candidato.getCargo() == null || candidato.getCargo().isBlank())
            throw new RuntimeException("Cargo é obrigatório");
    }

    private void validarFormatoEmail(String email) {
        if (!email.contains("@"))
            throw new RuntimeException("Formato de e-mail inválido");
    }

    private void atualizarCampoNome(Candidato existente, Candidato atualizado) {
        if (atualizado.getNome() != null) {
            String nome = atualizado.getNome().trim();
            if (nome.isBlank()) throw new RuntimeException("Nome não pode ser vazio");
            existente.setNome(nome);
        }
    }

    private void atualizarCampoEmail(Candidato existente, Candidato atualizado) {
        if (atualizado.getEmail() != null) {
            String email = atualizado.getEmail().trim();
            if (email.isBlank()) throw new RuntimeException("E-mail não pode ser vazio");
            validarFormatoEmail(email);
            if (!existente.getEmail().equals(email) && candidatoRepository.findByEmail(email).isPresent())
                throw new CandidatoEmailDuplicadoException(email);
            existente.setEmail(email);
        }
    }

    private void atualizarCampoSenha(Candidato existente, Candidato atualizado) {
        if (atualizado.getSenha() != null) {
            String senha = atualizado.getSenha().trim();
            if (senha.isBlank()) throw new RuntimeException("Senha não pode ser vazia");
            existente.setSenha(senha);
        }
    }

    private void atualizarCampoPartido(Candidato existente, Candidato atualizado) {
        if (atualizado.getPartido() != null) {
            String partido = atualizado.getPartido().trim();
            if (partido.isBlank()) throw new RuntimeException("Partido não pode ser vazio");
            existente.setPartido(partido);
        }
    }

    private void atualizarCampoCargo(Candidato existente, Candidato atualizado) {
        if (atualizado.getCargo() != null) {
            String cargo = atualizado.getCargo().trim();
            if (cargo.isBlank()) throw new RuntimeException("Cargo não pode ser vazio");
            existente.setCargo(cargo);
        }
    }

    private void atualizarCampoBiografia(Candidato existente, Candidato atualizado) {
        if (atualizado.getBiografia() != null)
            existente.setBiografia(atualizado.getBiografia().trim());
    }
}
