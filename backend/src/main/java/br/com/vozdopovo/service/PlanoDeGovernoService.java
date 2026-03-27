package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusPublicacao;

public interface PlanoDeGovernoService {

    PlanoDeGoverno criar(Long candidatoId, PlanoDeGoverno plano);

    /** Busca irrestrita — para uso interno (autenticado). */
    PlanoDeGoverno buscarPorId(Long id);

    /** Busca irrestrita por candidato — para uso interno (autenticado). */
    PlanoDeGoverno buscarPorCandidatoId(Long candidatoId);

    PlanoDeGoverno atualizarDados(Long id, PlanoDeGoverno planoAtualizado);

    PlanoDeGoverno atualizarStatus(Long id, StatusPublicacao status);

    /**
     * Busca pública por id: retorna o plano apenas se estiver PUBLICADO.
     * Usado por rotas GET sem autenticação.
     */
    PlanoDeGoverno buscarPublicoPorId(Long id);

    /**
     * Busca pública por candidato: retorna o plano do candidato apenas se estiver PUBLICADO.
     * Usado por rotas GET sem autenticação.
     */
    PlanoDeGoverno buscarPublicoPorCandidatoId(Long candidatoId);
}
