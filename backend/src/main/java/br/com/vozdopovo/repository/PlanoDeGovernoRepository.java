package br.com.vozdopovo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.PlanoDeGoverno;

public interface PlanoDeGovernoRepository extends JpaRepository<PlanoDeGoverno, Long> {

    Optional<PlanoDeGoverno> findByCandidatoId(Long candidatoId);

    boolean existsByCandidatoId(Long candidatoId);
}
