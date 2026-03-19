package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Interacao;
import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;
import java.util.List;

public interface InteracaoService {

    Interacao criar(Long eleitorId, Long candidatoId, Interacao interacao);
    Interacao buscarPorId(Long id);
    List<Interacao> listarPorCandidato(Long candidatoId);
    List<Interacao> listarPorEleitor(Long eleitorId);
    List<Interacao> listarPorStatus(StatusInteracao status);
    List<Interacao> listarPorTipo(TipoInteracao tipo);
    Interacao responder(Long interacaoId, String resposta);
    Interacao atualizarStatus(Long interacaoId, StatusInteracao status);
    Interacao atualizar(Long id, Interacao interacaoAtualizada);
    void deletar(Long id);
}
