package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.enums.StatusPublicacao;

import java.util.List;

public interface PropostaService {

    Proposta criar(Long temaId, Proposta proposta);
    Proposta buscarPorId(Long id);
    List<Proposta> listarPorTema(Long temaId);
    Proposta atualizarDados(Long id, Proposta propostaAtualizada);
    Proposta atualizarStatus(Long id, StatusPublicacao status);
}
