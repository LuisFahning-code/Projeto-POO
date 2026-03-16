package br.com.vozdopovo.repository;

import br.com.vozdopovo.entity.AnexoProposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnexoPropostaRepository extends JpaRepository<AnexoProposta, Long> {

    List<AnexoProposta> findByPropostaId(Long propostaId);
}
