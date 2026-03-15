package br.com.vozdopovo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.enums.StatusPublicacao;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    List<Proposta> findByPlanoDeGovernoId(Long planoId);

    List<Proposta> findByTemaId(Long temaId);

    List<Proposta> findByStatus(StatusPublicacao status);

    List<Proposta> findByTemaIdAndStatus(Long temaId, StatusPublicacao status);

    List<Proposta> findByPlanoDeGovernoIdAndStatus(Long planoId, StatusPublicacao status);
}
