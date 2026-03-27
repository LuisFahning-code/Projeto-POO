package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Interacao;
import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;
import java.util.List;

public interface InteracaoService {

    Interacao criar(Long eleitorId, Long candidatoId, Interacao interacao);
    Interacao buscarPorId(Long id);

    /** Listagem irrestrita por candidato — uso interno do controller após ownership verificado. */
    List<Interacao> listarPorCandidato(Long candidatoId);

    /** Listagem irrestrita por eleitor — uso interno do controller após ownership verificado. */
    List<Interacao> listarPorEleitor(Long eleitorId);

    /** Interações de um eleitor com um candidato específico. */
    List<Interacao> listarPorEleitorECandidato(Long eleitorId, Long candidatoId);

    /** Interações do candidato filtradas por status. */
    List<Interacao> listarPorCandidatoEStatus(Long candidatoId, StatusInteracao status);

    /** Interações do candidato filtradas por tipo. */
    List<Interacao> listarPorCandidatoETipo(Long candidatoId, TipoInteracao tipo);

    /** Interações do eleitor filtradas por status. */
    List<Interacao> listarPorEleitorEStatus(Long eleitorId, StatusInteracao status);

    /** Interações do eleitor filtradas por tipo. */
    List<Interacao> listarPorEleitorETipo(Long eleitorId, TipoInteracao tipo);

    Interacao responder(Long interacaoId, String resposta);
    Interacao atualizarStatus(Long interacaoId, StatusInteracao status);
}
