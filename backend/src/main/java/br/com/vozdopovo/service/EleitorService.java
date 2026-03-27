package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Eleitor;
import java.util.List;

public interface EleitorService {

    Eleitor criar(Eleitor eleitor);
    Eleitor buscarPorId(Long id);

    /**
     * Busca um eleitor pelo e-mail.
     * Usado internamente pelos controllers que precisam resolver o email
     * do token JWT para o objeto Eleitor correspondente.
     */
    Eleitor buscarPorEmail(String email);

    List<Eleitor> listarTodosAtivos();
    Eleitor atualizarDados(Long id, Eleitor eleitorAtualizado);
    void desativar(Long id);
}
