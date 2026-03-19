package br.com.vozdopovo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.repository.CandidatoRepository;
import br.com.vozdopovo.service.CandidatoService;

@Service
public class CandidatoServiceImpl implements CandidatoService {

    private final CandidatoRepository candidatoRepository;

    public CandidatoServiceImpl(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    // Retorna Cadastramento do Candidato
    @Override
    public Candidato criar(Candidato candidato) {

        normalizarCampos(candidato);

        validarCamposObrigatorios(candidato);

        validarFormatoEmail(candidato.getEmail());

        
        if (candidatoRepository.findByEmail(candidato.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um candidato com esse e-mail");
        }

        candidato.setStatus(StatusConta.ATIVA);

        return candidatoRepository.save(candidato);
    }

    // Retorna a busca de um candidato pelo Id
    @Override
    public Candidato buscarPorId(Long id) {
        return candidatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato não encontrado"));
    }

    // Retorna uma lista com todos os candidatos ativos
    @Override
    public List<Candidato> listarTodosAtivos() {
        return candidatoRepository.findByStatus(StatusConta.ATIVA);
    }

    // Retorna a busca da lista de candidatos com um nome
    @Override
    public List<Candidato> buscarPorNome(String nome) {
        return candidatoRepository.findByNomeContainingIgnoreCase(nome);
    }

    // Retorna a atualização dos dados do candidato
    @Override
    public Candidato atualizar(Long id, Candidato candidatoAtualizado) {

        Candidato candidatoExistente = buscarPorId(id);

        atualizarCampoNome(candidatoExistente, candidatoAtualizado);
        atualizarCampoEmail(candidatoExistente, candidatoAtualizado);
        atualizarCampoSenha(candidatoExistente, candidatoAtualizado);
        atualizarCampoPartido(candidatoExistente, candidatoAtualizado);
        atualizarCampoCargo(candidatoExistente, candidatoAtualizado);
        atualizarCampoBiografia(candidatoExistente, candidatoAtualizado);

        return candidatoRepository.save(candidatoExistente);
    }

    // Desativa a conta do candidato
    @Override
    public void desativar(Long id) {

        Candidato candidato = buscarPorId(id);

        if (candidato.getStatus() == StatusConta.DESATIVADA) {
            throw new RuntimeException("Candidato já está desativado");
        }

        candidato.setStatus(StatusConta.DESATIVADA);

        candidatoRepository.save(candidato);
    }

    // =============================
    //  MÉTODOS AUXILIARES
    // ============================= 

    // Verificação do preenchimento dos campos
    private void normalizarCampos(Candidato candidato) {

        if (candidato.getNome() != null) {
            candidato.setNome(candidato.getNome().trim());
        }

        if (candidato.getEmail() != null) {
            candidato.setEmail(candidato.getEmail().trim());
        }

        if (candidato.getPartido() != null) {
            candidato.setPartido(candidato.getPartido().trim());
        }

        if (candidato.getCargo() != null) {
            candidato.setCargo(candidato.getCargo().trim());
        }

        if (candidato.getBiografia() != null) {
            candidato.setBiografia(candidato.getBiografia().trim());
        }
    }

    // Validação de campos que devem ter preenchimento obrigatório
    private void validarCamposObrigatorios(Candidato candidato) {

        if (candidato.getNome() == null || candidato.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (candidato.getEmail() == null || candidato.getEmail().isBlank()) {
            throw new RuntimeException("E-mail é obrigatório");
        }

        if (candidato.getSenha() == null || candidato.getSenha().isBlank()) {
            throw new RuntimeException("Senha é obrigatória");
        }

        if (candidato.getPartido() == null || candidato.getPartido().isBlank()) {
            throw new RuntimeException("Partido é obrigatório");
        }

        if (candidato.getCargo() == null || candidato.getCargo().isBlank()) {
            throw new RuntimeException("Cargo é obrigatório");
        }
    }

    // Validação do formato do email cadastrado
    private void validarFormatoEmail(String email) {

        if (!email.contains("@")) {
            throw new RuntimeException("Formato de e-mail inválido");
        }
    }

    // Atualização, com verificação, do nome do candidato
    private void atualizarCampoNome(Candidato existente, Candidato atualizado) {

        if (atualizado.getNome() != null) {

            String nome = atualizado.getNome().trim();

            if (nome.isBlank()) {
                throw new RuntimeException("Nome não pode ser vazio");
            }

            existente.setNome(nome);
        }
    }

    // Atualização, com verificação, do email do candidato
    private void atualizarCampoEmail(Candidato existente, Candidato atualizado) {

        if (atualizado.getEmail() != null) {

            String email = atualizado.getEmail().trim();

            if (email.isBlank()) {
                throw new RuntimeException("E-mail não pode ser vazio");
            }

            validarFormatoEmail(email);

            if (!existente.getEmail().equals(email)
                    && candidatoRepository.findByEmail(email).isPresent()) {

                throw new RuntimeException("Já existe um candidato com esse e-mail");
            }

            existente.setEmail(email);
        }
    }

    // Atualização, com verificação, da senha do candidato
    private void atualizarCampoSenha(Candidato existente, Candidato atualizado) {

        if (atualizado.getSenha() != null) {

            String senha = atualizado.getSenha().trim();

            if (senha.isBlank()) {
                throw new RuntimeException("Senha não pode ser vazia");
            }

            existente.setSenha(senha);
        }
    }

    // Atualização, com verificação, do partido do candidato
    private void atualizarCampoPartido(Candidato existente, Candidato atualizado) {

        if (atualizado.getPartido() != null) {

            String partido = atualizado.getPartido().trim();

            if (partido.isBlank()) {
                throw new RuntimeException("Partido não pode ser vazio");
            }

            existente.setPartido(partido);
        }
    }

    // Atualização, com verificação, do cargo do candidato
    private void atualizarCampoCargo(Candidato existente, Candidato atualizado) {

        if (atualizado.getCargo() != null) {

            String cargo = atualizado.getCargo().trim();

            if (cargo.isBlank()) {
                throw new RuntimeException("Cargo não pode ser vazio");
            }

            existente.setCargo(cargo);
        }
    }

    // Atualização, com verificação, da biografia do candidato
    private void atualizarCampoBiografia(Candidato existente, Candidato atualizado) {

        if (atualizado.getBiografia() != null) {

            String bio = atualizado.getBiografia().trim();

            existente.setBiografia(bio);
        }
    }
}
