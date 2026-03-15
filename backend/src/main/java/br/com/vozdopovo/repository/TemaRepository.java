package br.com.vozdopovo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long> {

    List<Tema> findByPlanoDeGovernoId(Long planoId);

    boolean existsByNomeAndPlanoDeGovernoId(String nome, Long planoId);
}
