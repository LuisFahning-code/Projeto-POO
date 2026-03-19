package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Eleitor;
import java.util.List;

public interface EleitorService {

    Eleitor criar(Eleitor eleitor);
    Eleitor buscarPorId(Long id);
    List<Eleitor> listarTodosAtivos();
    Eleitor atualizar(Long id, Eleitor eleitorAtualizado);
    void desativar(Long id);
}
