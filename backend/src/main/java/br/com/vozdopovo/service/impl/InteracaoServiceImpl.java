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
    @Override
    public Interacao criar(Long eleitorId, Long candidatoId, Interacao interacao) {
        if (interacao == null) {
            throw new RuntimeException("A interação não pode ser nula.");
        }

        Eleitor eleitor = eleitorRepository.findById(eleitorId)
                .orElseThrow(() -> new RuntimeException("Eleitor não encontrado com id: " + eleitorId));

        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> new RuntimeException("Candidato não encontrado com id: " + candidatoId));

        if (interacao.getConteudo() == null || interacao.getConteudo().isBlank()) {
            throw new RuntimeException("O conteúdo da interação é obrigatório.");
        }

        if (interacao.getTipo() == null) {
            throw new RuntimeException("O tipo da interação é obrigatório.");
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
    @Override
    public Interacao buscarPorId(Long id) {
        return interacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interação não encontrada com id: " + id));
    }

    // Retorna a lista de todas as Interações associadas a um candidato
    @Override
    public List<Interacao> listarPorCandidato(Long candidatoId) {
        return interacaoRepository.findByCandidatoId(candidatoId);
    }

    // Retona a lista de todas as Interações associadas a um eleitor
    @Override
    public List<Interacao> listarPorEleitor(Long eleitorId) {
        return interacaoRepository.findByEleitorId(eleitorId);
    }

    // Retorna a lista de todas as Interações associadas a um status
    @Override
    public List<Interacao> listarPorStatus(StatusInteracao status) {
        if (status == null) {
            throw new RuntimeException("O status da interação deve ser informado.");
        }

        return interacaoRepository.findByStatus(status);
    }

    // Retorna a lista de todas as Interações com base no seu tipo 
    @Override
    public List<Interacao> listarPorTipo(TipoInteracao tipo) {
        if (tipo == null) {
            throw new RuntimeException("O tipo da interação deve ser informado.");
        }

        return interacaoRepository.findByTipo(tipo);
    }

    // Retorna a resposta ao Eleitor
    @Override
    public Interacao responder(Long interacaoId, String resposta) {
        Interacao interacaoExistente = buscarPorId(interacaoId);

        if (resposta == null || resposta.isBlank()) {
            throw new RuntimeException("A resposta não pode ser vazia.");
        }

        interacaoExistente.setResposta(resposta);
        interacaoExistente.setDataResposta(LocalDateTime.now());
        interacaoExistente.setStatus(StatusInteracao.RESPONDIDA);

        return interacaoRepository.save(interacaoExistente);
    }

    // Retorna a atualização do Status de uma Interação
    @Override
    public Interacao atualizarStatus(Long interacaoId, StatusInteracao status) {
        Interacao interacaoExistente = buscarPorId(interacaoId);

        if (status == null) {
            throw new RuntimeException("O status da interação deve ser informado.");
        }

        interacaoExistente.setStatus(status);

        return interacaoRepository.save(interacaoExistente);
    }
}
