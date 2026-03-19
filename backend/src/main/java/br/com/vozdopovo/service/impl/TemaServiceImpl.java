package br.com.vozdopovo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.repository.TemaRepository;
import br.com.vozdopovo.service.TemaService;

@Service
public class TemaServiceImpl implements TemaService {

    private final TemaRepository temaRepository;
    private final PlanoDeGovernoRepository planoDeGovernoRepository;

    public TemaServiceImpl(TemaRepository temaRepository,
                           PlanoDeGovernoRepository planoDeGovernoRepository) {
        this.temaRepository = temaRepository;
        this.planoDeGovernoRepository = planoDeGovernoRepository;
    }

    @Override
    public Tema criar(Long planoId, Tema tema) {
        PlanoDeGoverno plano = planoDeGovernoRepository.findById(planoId)
                .orElseThrow(() -> new RuntimeException("Plano de governo não encontrado."));

        tema.setPlanoDeGoverno(plano);
        tema.setDataCriacao(LocalDateTime.now());
        tema.setDataAtualizacao(LocalDateTime.now());

        if (tema.getStatus() == null) {
            tema.setStatus(StatusPublicacao.RASCUNHO);
        }

        return temaRepository.save(tema);
    }

    // Retorna a busca de um Tema pelo seu Id
    @Override
    public Tema buscarPorId(Long id) {
        return temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema não encontrado."));
    }

    // Retorna a lista de Temas anexados a um Plano, buscado pelo Id
    @Override
    public List<Tema> listarPorPlano(Long planoId) {
        PlanoDeGoverno plano = planoDeGovernoRepository.findById(planoId)
                .orElseThrow(() -> new RuntimeException("Plano de governo não encontrado."));

        return temaRepository.findByPlanoDeGovernoId(plano.getId());
    }

    // Retorna os dados atualizados do Tema, caso existam mudanças
    @Override
    public Tema atualizarDados(Long id, Tema temaAtualizado) {
        Tema temaExistente = buscarPorId(id);

        boolean alterou = false;

        if (temaAtualizado.getTitulo() != null && !temaAtualizado.getTitulo().isBlank()) {
            temaExistente.setTitulo(temaAtualizado.getTitulo());
            alterou = true;
        }

        if (temaAtualizado.getDescricao() != null) {
            temaExistente.setDescricao(temaAtualizado.getDescricao());
            alterou = true;
        }

        if (alterou) {
            temaExistente.setDataAtualizacao(LocalDateTime.now());
        }

        return temaRepository.save(temaExistente);
    }

    // Retorna a atualização do status do Tema, entre RASCUNHO, PUBLICADO e ARQUIVADO
    @Override
    public Tema atualizarStatus(Long id, StatusPublicacao status) {
        Tema temaExistente = buscarPorId(id);

        if (status == null) {
            throw new RuntimeException("O status do tema deve ser informado.");
        }

        temaExistente.setStatus(status);
        temaExistente.setDataAtualizacao(LocalDateTime.now());

        return temaRepository.save(temaExistente);
    }
}
