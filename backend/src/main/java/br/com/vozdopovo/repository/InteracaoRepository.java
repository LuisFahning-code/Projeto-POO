package br.com.vozdopovo.repository;

import br.com.vozdopovo.entity.Interacao;
import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteracaoRepository extends JpaRepository<Interacao, Long> {

    List<Interacao> findByCandidatoId(Long candidatoId);

    List<Interacao> findByEleitorId(Long eleitorId);

    List<Interacao> findByStatus(StatusInteracao status);

    List<Interacao> findByTipo(TipoInteracao tipo);

    // Filtros para candidato
    List<Interacao> findByCandidatoIdAndStatus(Long candidatoId, StatusInteracao status);
    List<Interacao> findByCandidatoIdAndTipo(Long candidatoId, TipoInteracao tipo);

    // Filtro duplo eleitor + candidato (eleitor vendo suas interações com um candidato específico)
    List<Interacao> findByEleitorIdAndCandidatoId(Long eleitorId, Long candidatoId);

    // Filtros para eleitor (por status e por tipo)
    List<Interacao> findByEleitorIdAndStatus(Long eleitorId, StatusInteracao status);
    List<Interacao> findByEleitorIdAndTipo(Long eleitorId, TipoInteracao tipo);
}
