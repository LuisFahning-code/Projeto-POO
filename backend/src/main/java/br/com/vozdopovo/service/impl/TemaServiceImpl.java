package br.com.vozdopovo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.exception.plano.PlanoDeGovernoNotFoundException;
import br.com.vozdopovo.exception.tema.TemaNotFoundException;
import br.com.vozdopovo.exception.validation.CampoObrigatorioException;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.repository.TemaRepository;
import br.com.vozdopovo.service.GeradorTxtService;
import br.com.vozdopovo.service.TemaService;

@Service
public class TemaServiceImpl implements TemaService {

    private final TemaRepository temaRepository;
    private final PlanoDeGovernoRepository planoDeGovernoRepository;
    private final GeradorTxtService geradorTxtService;

    public TemaServiceImpl(TemaRepository temaRepository,
                           PlanoDeGovernoRepository planoDeGovernoRepository,
                           GeradorTxtService geradorTxtService) {
        this.temaRepository = temaRepository;
        this.planoDeGovernoRepository = planoDeGovernoRepository;
        this.geradorTxtService = geradorTxtService;
    }

    @Transactional
    @Override
    public Tema criar(Long planoId, Tema tema) {
        PlanoDeGoverno plano = planoDeGovernoRepository.findById(planoId)
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(planoId));

        tema.setPlanoDeGoverno(plano);
        tema.setDataCriacao(LocalDateTime.now());
        tema.setDataAtualizacao(LocalDateTime.now());

        if (tema.getStatus() == null) {
            tema.setStatus(StatusPublicacao.RASCUNHO);
        }

        return temaRepository.save(tema);
    }

    @Transactional(readOnly = true)
    @Override
    public Tema buscarPorId(Long id) {
        return temaRepository.findById(id)
                .orElseThrow(() -> new TemaNotFoundException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Tema> listarPorPlano(Long planoId) {
        PlanoDeGoverno plano = planoDeGovernoRepository.findById(planoId)
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(planoId));

        return temaRepository.findByPlanoDeGovernoId(plano.getId());
    }

    /**
     * Busca pública por id: retorna o tema somente se estiver PUBLICADO.
     * Rascunhos e arquivados geram 404 para usuários não autenticados.
     */
    @Transactional(readOnly = true)
    @Override
    public Tema buscarPublicoPorId(Long id) {
        Tema tema = buscarPorId(id);
        if (tema.getStatus() != StatusPublicacao.PUBLICADO) {
            throw new TemaNotFoundException(id);
        }
        return tema;
    }

    /**
     * Listagem pública por plano: retorna apenas temas PUBLICADOS.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Tema> listarPublicosPorPlano(Long planoId) {
        if (!planoDeGovernoRepository.existsById(planoId)) {
            throw new PlanoDeGovernoNotFoundException(planoId);
        }
        return temaRepository.findByPlanoDeGovernoIdAndStatus(planoId, StatusPublicacao.PUBLICADO);
    }

    @Transactional
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

            PlanoDeGoverno plano = temaExistente.getPlanoDeGoverno();
            if (plano.getStatus() == StatusPublicacao.PUBLICADO) {
                geradorTxtService.gerarTxt(plano);
            }
        }

        return temaRepository.save(temaExistente);
    }

    @Transactional
    @Override
    public Tema atualizarStatus(Long id, StatusPublicacao status) {
        Tema temaExistente = buscarPorId(id);

        if (status == null) {
            throw new CampoObrigatorioException("status");
        }

        temaExistente.setStatus(status);
        temaExistente.setDataAtualizacao(LocalDateTime.now());

        Tema salvo = temaRepository.save(temaExistente);

        PlanoDeGoverno plano = salvo.getPlanoDeGoverno();
        if (plano.getStatus() == StatusPublicacao.PUBLICADO) {
            geradorTxtService.gerarTxt(plano);
        }

        return salvo;
    }
}
