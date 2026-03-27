package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.enums.StatusPublicacao;

import java.util.List;

public interface PropostaService {

    Proposta criar(Long temaId, Proposta proposta);

    /** Busca irrestrita — para uso interno (autenticado). */
    Proposta buscarPorId(Long id);

    /** Listagem irrestrita — para uso interno (autenticado). */
    List<Proposta> listarPorTema(Long temaId);

    Proposta atualizarDados(Long id, Proposta propostaAtualizada);

    Proposta atualizarStatus(Long id, StatusPublicacao status);

    /**
     * Busca pública por id: retorna a proposta apenas se estiver PUBLICADA.
     * Usado por rotas GET sem autenticação.
     */
    Proposta buscarPublicaPorId(Long id);

    /**
     * Listagem pública por tema: retorna apenas propostas PUBLICADAS.
     * Usado por rotas GET sem autenticação.
     */
    List<Proposta> listarPublicasPorTema(Long temaId);
}
