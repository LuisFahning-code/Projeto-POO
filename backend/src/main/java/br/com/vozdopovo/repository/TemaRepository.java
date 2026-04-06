package br.com.vozdopovo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;

public interface TemaRepository extends JpaRepository<Tema, Long> {

    List<Tema> findByPlanoDeGovernoId(Long planoId);

    boolean existsByTituloAndPlanoDeGovernoId(String titulo, Long planoId);

    /**
     * Usado nas rotas públicas: retorna apenas temas com o status informado
     * (PUBLICADO) para um determinado plano. Evita expor rascunhos via GET público.
     */
    List<Tema> findByPlanoDeGovernoIdAndStatus(Long planoId, StatusPublicacao status);
}
