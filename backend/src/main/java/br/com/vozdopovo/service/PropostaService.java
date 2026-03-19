package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Proposta;
import java.util.List;

public interface PropostaService {

    Proposta criar(Long temaId, Proposta proposta);
    Proposta buscarPorId(Long id);
    List<Proposta> listarPorTema(Long temaId);
    Proposta atualizar(Long id, Proposta propostaAtualizada);
    void deletar(Long id);
}
