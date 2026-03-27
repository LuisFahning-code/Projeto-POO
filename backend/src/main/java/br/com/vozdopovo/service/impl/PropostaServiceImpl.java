package br.com.vozdopovo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.exception.proposta.PropostaNotFoundException;
import br.com.vozdopovo.exception.validation.CampoObrigatorioException;
import br.com.vozdopovo.repository.PropostaRepository;
import br.com.vozdopovo.service.GeradorTxtService;
import br.com.vozdopovo.service.PropostaService;
import br.com.vozdopovo.service.TemaService;

@Service
public class PropostaServiceImpl implements PropostaService {

    private final PropostaRepository propostaRepository;
    private final TemaService temaService;
    private final GeradorTxtService geradorTxtService;

    public PropostaServiceImpl(PropostaRepository propostaRepository,
                                TemaService temaService,
                                GeradorTxtService geradorTxtService) {
        this.propostaRepository = propostaRepository;
        this.temaService = temaService;
        this.geradorTxtService = geradorTxtService;
    }

    @Transactional
    @Override
    public Proposta criar(Long temaId, Proposta proposta) {
        var tema = temaService.buscarPorId(temaId);

        proposta.setTema(tema);
        proposta.setPlanoDeGoverno(tema.getPlanoDeGoverno());
        proposta.setStatus(StatusPublicacao.RASCUNHO);
        proposta.setDataCriacao(LocalDateTime.now());
        proposta.setDataAtualizacao(LocalDateTime.now());

        return propostaRepository.save(proposta);
    }

    @Transactional(readOnly = true)
    @Override
    public Proposta buscarPorId(Long id) {
        return propostaRepository.findById(id)
                .orElseThrow(() -> new PropostaNotFoundException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Proposta> listarPorTema(Long temaId) {
        return propostaRepository.findByTemaId(temaId);
    }

    /**
     * Busca pública por id: retorna a proposta somente se estiver PUBLICADA.
     * Rascunhos e arquivados geram 404 para usuários não autenticados.
     */
    @Transactional(readOnly = true)
    @Override
    public Proposta buscarPublicaPorId(Long id) {
        Proposta proposta = buscarPorId(id);
        if (proposta.getStatus() != StatusPublicacao.PUBLICADO) {
            throw new PropostaNotFoundException(id);
        }
        return proposta;
    }

    /**
     * Listagem pública por tema: retorna apenas propostas PUBLICADAS.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Proposta> listarPublicasPorTema(Long temaId) {
        return propostaRepository.findByTemaIdAndStatus(temaId, StatusPublicacao.PUBLICADO);
    }

    @Transactional
    @Override
    public Proposta atualizarDados(Long id, Proposta propostaAtualizada) {
        Proposta propostaExistente = buscarPorId(id);

        boolean alterou = false;

        if (propostaAtualizada.getTitulo() != null && !propostaAtualizada.getTitulo().isBlank()) {
            propostaExistente.setTitulo(propostaAtualizada.getTitulo());
            alterou = true;
        }

        if (propostaAtualizada.getResumo() != null && !propostaAtualizada.getResumo().isBlank()) {
            propostaExistente.setResumo(propostaAtualizada.getResumo());
            alterou = true;
        }

        if (propostaAtualizada.getDetalhamento() != null) {
            propostaExistente.setDetalhamento(propostaAtualizada.getDetalhamento());
            alterou = true;
        }

        if (alterou) {
            propostaExistente.setDataAtualizacao(LocalDateTime.now());

            PlanoDeGoverno plano = propostaExistente.getPlanoDeGoverno();
            if (propostaExistente.getStatus() == StatusPublicacao.PUBLICADO
                    && plano.getStatus() == StatusPublicacao.PUBLICADO) {
                geradorTxtService.gerarTxt(plano);
            }
        }

        return propostaRepository.save(propostaExistente);
    }

    @Transactional
    @Override
    public Proposta atualizarStatus(Long id, StatusPublicacao status) {
        Proposta propostaExistente = buscarPorId(id);

        if (status == null) {
            throw new CampoObrigatorioException("status");
        }

        propostaExistente.setStatus(status);
        propostaExistente.setDataAtualizacao(LocalDateTime.now());

        Proposta salva = propostaRepository.save(propostaExistente);

        PlanoDeGoverno plano = salva.getPlanoDeGoverno();
        if (plano.getStatus() == StatusPublicacao.PUBLICADO) {
            geradorTxtService.gerarTxt(plano);
        }

        return salva;
    }
}
