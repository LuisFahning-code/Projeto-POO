package br.com.vozdopovo.service.impl;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.entity.Interacao;
import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;
import br.com.vozdopovo.repository.CandidatoRepository;
import br.com.vozdopovo.repository.EleitorRepository;
import br.com.vozdopovo.repository.InteracaoRepository;
import br.com.vozdopovo.service.InteracaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vozdopovo.exception.eleitor.EleitorNotFoundException;
import br.com.vozdopovo.exception.validation.CampoObrigatorioException;
import br.com.vozdopovo.exception.candidato.CandidatoNotFoundException;
import br.com.vozdopovo.exception.interacao.InteracaoNaoPermiteRespostaException;
import br.com.vozdopovo.exception.interacao.InteracaoNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteracaoServiceImpl implements InteracaoService {

    private final InteracaoRepository interacaoRepository;
    private final EleitorRepository eleitorRepository;
    private final CandidatoRepository candidatoRepository;

    public InteracaoServiceImpl(InteracaoRepository interacaoRepository,
                                EleitorRepository eleitorRepository,
                                CandidatoRepository candidatoRepository) {
        this.interacaoRepository = interacaoRepository;
        this.eleitorRepository = eleitorRepository;
        this.candidatoRepository = candidatoRepository;
    }

    // Retorno do cadastramento de uma nova Interação no banco// Retorna a busca da proposta pelo seu Id
    @Transactional 
    @Override
    public Interacao criar(Long eleitorId, Long candidatoId, Interacao interacao) {
        if (interacao == null) {
            throw new CampoObrigatorioException("interação");
        }

        Eleitor eleitor = eleitorRepository.findById(eleitorId)
                .orElseThrow(() -> new EleitorNotFoundException(eleitorId));

        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> new CandidatoNotFoundException(candidatoId));

        if (interacao.getConteudo() == null || interacao.getConteudo().isBlank()) {
            throw new CampoObrigatorioException("conteudo");
        }

        if (interacao.getTipo() == null) {
            throw new CampoObrigatorioException("tipo");
        }

        interacao.setEleitor(eleitor);
        interacao.setCandidato(candidato);
        interacao.setStatus(StatusInteracao.RECEBIDA);
        interacao.setResposta(null);
        interacao.setDataInicio(LocalDateTime.now());
        interacao.setDataResposta(null);

        return interacaoRepository.save(interacao);
    }

    // Retorno da busca de uma Interação pelo seu Id as proposta de um Tema
    @Transactional (readOnly = true)
    @Override
    public Interacao buscarPorId(Long id) {
        return interacaoRepository.findById(id)
                .orElseThrow(() -> new InteracaoNotFoundException(id));
    }

    // Retorna a lista de todas as Interações associadas a um candidato
    @Transactional (readOnly = true)
    @Override
    public List<Interacao> listarPorCandidato(Long candidatoId) {
        return interacaoRepository.findByCandidatoId(candidatoId);
    }

    // Retona a lista de todas as Interações associadas a um eleitor
    @Transactional (readOnly = true)
    @Override
    public List<Interacao> listarPorEleitor(Long eleitorId) {
        return interacaoRepository.findByEleitorId(eleitorId);
    }

    // Retorna a lista de todas as Interações associadas a um status
    @Transactional (readOnly = true)
    @Override
    public List<Interacao> listarPorStatus(StatusInteracao status) {
        if (status == null) {
            throw new CampoObrigatorioException("status");
        }

        return interacaoRepository.findByStatus(status);
    }

    // Retorna a lista de todas as Interações com base no seu tipo 
    @Transactional (readOnly = true)
    @Override
    public List<Interacao> listarPorTipo(TipoInteracao tipo) {
        if (tipo == null) {
            throw new CampoObrigatorioException("tipo");
        }

        return interacaoRepository.findByTipo(tipo);
    }

    // Retorna a resposta ao Eleitor
    @Transactional 
    @Override
    public Interacao responder(Long interacaoId, String resposta) {
        Interacao interacaoExistente = buscarPorId(interacaoId);

        if (interacaoExistente.getStatus() == StatusInteracao.FINALIZADA ||
            interacaoExistente.getStatus() == StatusInteracao.RESPONDIDA) {
            throw new InteracaoNaoPermiteRespostaException(interacaoId, interacaoExistente.getStatus());
        }

        if (resposta == null || resposta.isBlank()) {
            throw new CampoObrigatorioException("resposta");
        }

        interacaoExistente.setResposta(resposta);
        interacaoExistente.setDataResposta(LocalDateTime.now());
        interacaoExistente.setStatus(StatusInteracao.RESPONDIDA);

        return interacaoRepository.save(interacaoExistente);
    }

    // Retorna a atualização do Status de uma Interação
    @Transactional 
    @Override
    public Interacao atualizarStatus(Long interacaoId, StatusInteracao status) {
        Interacao interacaoExistente = buscarPorId(interacaoId);

        if (status == null) {
            throw new CampoObrigatorioException("status");
        }

        interacaoExistente.setStatus(status);

        return interacaoRepository.save(interacaoExistente);
    }
}
