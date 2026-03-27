package br.com.vozdopovo.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.exception.candidato.CandidatoNotFoundException;
import br.com.vozdopovo.exception.plano.PlanoDeGovernoJaExisteException;
import br.com.vozdopovo.exception.plano.PlanoDeGovernoNotFoundException;
import br.com.vozdopovo.exception.validation.CampoObrigatorioException;
import br.com.vozdopovo.repository.CandidatoRepository;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.service.GeradorTxtService;
import br.com.vozdopovo.service.PlanoDeGovernoService;

@Service
public class PlanoDeGovernoServiceImpl implements PlanoDeGovernoService {

    private final PlanoDeGovernoRepository planoDeGovernoRepository;
    private final CandidatoRepository candidatoRepository;
    private final GeradorTxtService geradorTxtService;

    public PlanoDeGovernoServiceImpl(PlanoDeGovernoRepository planoDeGovernoRepository,
                                     CandidatoRepository candidatoRepository,
                                     GeradorTxtService geradorTxtService) {
        this.planoDeGovernoRepository = planoDeGovernoRepository;
        this.candidatoRepository = candidatoRepository;
        this.geradorTxtService = geradorTxtService;
    }

    @Transactional
    @Override
    public PlanoDeGoverno criar(Long candidatoId, PlanoDeGoverno plano) {
        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> new CandidatoNotFoundException(candidatoId));

        if (planoDeGovernoRepository.findByCandidatoId(candidatoId).isPresent()) {
            throw new PlanoDeGovernoJaExisteException(candidato.getId());
        }

        plano.setCandidato(candidato);
        plano.setDataCriacao(LocalDateTime.now());
        plano.setDataAtualizacao(LocalDateTime.now());

        if (plano.getStatus() == null) {
            plano.setStatus(StatusPublicacao.RASCUNHO);
        }

        return planoDeGovernoRepository.save(plano);
    }

    @Transactional(readOnly = true)
    @Override
    public PlanoDeGoverno buscarPorId(Long id) {
        return planoDeGovernoRepository.findById(id)
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public PlanoDeGoverno buscarPorCandidatoId(Long candidatoId) {
        return planoDeGovernoRepository.findByCandidatoId(candidatoId)
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(candidatoId));
    }

    /**
     * Busca pública por id: retorna o plano somente se estiver PUBLICADO.
     * Rascunhos e arquivados geram 404, impedindo que usuários não autenticados
     * descubram conteúdo ainda não publicado.
     */
    @Transactional(readOnly = true)
    @Override
    public PlanoDeGoverno buscarPublicoPorId(Long id) {
        PlanoDeGoverno plano = buscarPorId(id);
        if (plano.getStatus() != StatusPublicacao.PUBLICADO) {
            throw new PlanoDeGovernoNotFoundException(id);
        }
        return plano;
    }

    /**
     * Busca pública por candidato: retorna o plano somente se estiver PUBLICADO.
     */
    @Transactional(readOnly = true)
    @Override
    public PlanoDeGoverno buscarPublicoPorCandidatoId(Long candidatoId) {
        return planoDeGovernoRepository
                .findByCandidatoIdAndStatus(candidatoId, StatusPublicacao.PUBLICADO)
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(candidatoId));
    }

    @Transactional
    @Override
    public PlanoDeGoverno atualizarDados(Long id, PlanoDeGoverno planoAtualizado) {
        PlanoDeGoverno planoExistente = buscarPorId(id);

        boolean alterou = false;

        if (planoAtualizado.getTitulo() != null && !planoAtualizado.getTitulo().isBlank()) {
            planoExistente.setTitulo(planoAtualizado.getTitulo());
            alterou = true;
        }

        if (planoAtualizado.getApresentacao() != null) {
            planoExistente.setApresentacao(planoAtualizado.getApresentacao());
            alterou = true;
        }

        if (alterou) {
            planoExistente.setDataAtualizacao(LocalDateTime.now());

            if (planoExistente.getStatus() == StatusPublicacao.PUBLICADO) {
                geradorTxtService.gerarTxt(planoExistente);
            }
        }

        return planoDeGovernoRepository.save(planoExistente);
    }

    @Transactional
    @Override
    public PlanoDeGoverno atualizarStatus(Long id, StatusPublicacao status) {
        PlanoDeGoverno planoExistente = buscarPorId(id);

        if (status == null) {
            throw new CampoObrigatorioException("status");
        }

        StatusPublicacao statusAnterior = planoExistente.getStatus();
        planoExistente.setStatus(status);
        planoExistente.setDataAtualizacao(LocalDateTime.now());

        PlanoDeGoverno salvo = planoDeGovernoRepository.save(planoExistente);

        if (status == StatusPublicacao.PUBLICADO && statusAnterior != StatusPublicacao.PUBLICADO) {
            geradorTxtService.gerarTxt(salvo);
        }

        return salvo;
    }
}
