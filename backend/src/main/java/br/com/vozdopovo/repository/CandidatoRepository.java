package br.com.vozdopovo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.enums.StatusConta;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    Optional<Candidato> findByEmail(String email);

    boolean existsByEmail(String email);

    /**
     * Busca pública por nome: retorna apenas candidatos com conta ATIVA.
     * Candidatos desativados não aparecem nos resultados.
     */
    List<Candidato> findByNomeContainingIgnoreCaseAndStatus(String nome, StatusConta status);

    List<Candidato> findByStatus(StatusConta status);

    Optional<Candidato> findByIdAndStatus(Long id, StatusConta status);
}
