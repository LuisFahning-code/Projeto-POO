package br.com.vozdopovo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

    Optional<Candidato> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Candidato> findByNomeContainingIgnoreCase(String nome);
}
