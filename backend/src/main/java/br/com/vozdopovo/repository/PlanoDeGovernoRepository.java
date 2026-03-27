package br.com.vozdopovo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusPublicacao;

public interface PlanoDeGovernoRepository extends JpaRepository<PlanoDeGoverno, Long> {

    Optional<PlanoDeGoverno> findByCandidatoId(Long candidatoId);

    boolean existsByCandidatoId(Long candidatoId);

    /**
     * Usado nas rotas públicas: retorna o plano de um candidato apenas se estiver
     * com o status informado (PUBLICADO). Evita expor rascunhos via GET público.
     */
    Optional<PlanoDeGoverno> findByCandidatoIdAndStatus(Long candidatoId, StatusPublicacao status);
}
