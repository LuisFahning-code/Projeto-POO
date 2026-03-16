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

    List<Interacao> findByCandidatoIdAndStatus(Long candidatoId, StatusInteracao status);

    List<Interacao> findByCandidatoIdAndTipo(Long candidatoId, TipoInteracao tipo);
}
