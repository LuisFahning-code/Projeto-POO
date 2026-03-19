package br.com.vozdopovo.service;

import java.util.List;

import br.com.vozdopovo.entity.Candidato;

public interface CandidatoService {

    Candidato criar(Candidato candidato);
    Candidato buscarPorId(Long id);
    List<Candidato> buscarPorNome(String nome);
    List<Candidato> listarTodosAtivos();
    Candidato atualizar(Long id, Candidato candidatoAtualizado);
    void desativar(Long id);
}
