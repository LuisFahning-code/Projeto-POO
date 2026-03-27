package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;

import java.util.List;

public interface TemaService {

    Tema criar(Long planoId, Tema tema);

    /** Busca irrestrita — para uso interno (autenticado). */
    Tema buscarPorId(Long id);

    /** Listagem irrestrita — para uso interno (autenticado). */
    List<Tema> listarPorPlano(Long planoId);

    Tema atualizarDados(Long id, Tema temaAtualizado);

    Tema atualizarStatus(Long id, StatusPublicacao status);

    /**
     * Busca pública por id: retorna o tema apenas se estiver PUBLICADO.
     * Usado por rotas GET sem autenticação.
     */
    Tema buscarPublicoPorId(Long id);

    /**
     * Listagem pública por plano: retorna apenas temas PUBLICADOS.
     * Usado por rotas GET sem autenticação.
     */
    List<Tema> listarPublicosPorPlano(Long planoId);
}
