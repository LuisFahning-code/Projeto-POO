package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;

import java.util.List;

public interface TemaService {
    Tema criar(Long planoId, Tema tema);
    Tema buscarPorId(Long id);
    List<Tema> listarPorPlano(Long planoId);
    Tema atualizarDados(Long id, Tema temaAtualizado);
    Tema atualizarStatus(Long id, StatusPublicacao status);
}
