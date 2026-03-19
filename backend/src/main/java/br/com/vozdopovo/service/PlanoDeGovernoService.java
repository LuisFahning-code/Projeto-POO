package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusPublicacao;

public interface PlanoDeGovernoService {

    PlanoDeGoverno criar(Long candidatoId, PlanoDeGoverno plano);
    PlanoDeGoverno buscarPorId(Long id);
    PlanoDeGoverno buscarPorCandidatoId(Long candidatoId);
    PlanoDeGoverno atualizarDados(Long id, PlanoDeGoverno planoAtualizado);
    PlanoDeGoverno atualizarStatus(Long id, StatusPublicacao status);
}
