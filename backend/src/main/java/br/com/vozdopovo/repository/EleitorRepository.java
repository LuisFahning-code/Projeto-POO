package br.com.vozdopovo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.enums.StatusConta;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {

    Optional<Eleitor> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Eleitor> findByStatus(StatusConta status);
}
