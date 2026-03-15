package br.com.vozdopovo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.Eleitor;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

    Optional<Eleitor> findByEmail(String email);

    boolean existsByEmail(String email);
}
