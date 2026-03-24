package br.com.vozdopovo.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.exception.candidato.CandidatoNotFoundException;
import br.com.vozdopovo.repository.CandidatoRepository;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.service.PlanoDeGovernoService;
import br.com.vozdopovo.exception.candidato.CandidatoNotFoundException;
import br.com.vozdopovo.exception.plano.PlanoDeGovernoNotFoundException;
import br.com.vozdopovo.exception.validation.CampoObrigatorioException;
@Service
public class PlanoDeGovernoServiceImpl implements PlanoDeGovernoService {

    private final PlanoDeGovernoRepository planoDeGovernoRepository;
    private final CandidatoRepository candidatoRepository;

    public PlanoDeGovernoServiceImpl(PlanoDeGovernoRepository planoDeGovernoRepository,
                                     CandidatoRepository candidatoRepository) {
        this.planoDeGovernoRepository = planoDeGovernoRepository;
        this.candidatoRepository = candidatoRepository;
    }

    @Override
    public PlanoDeGoverno criar(Long candidatoId, PlanoDeGoverno plano) {
        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> CandidatoNotFoundException(candidatoId));

        if (planoDeGovernoRepository.findByCandidatoId(candidatoId).isPresent()) {
            throw new RuntimeException("Este candidato já possui um plano de governo cadastrado.");
        }

        plano.setCandidato(candidato);
        plano.setDataCriacao(LocalDateTime.now());
        plano.setDataAtualizacao(LocalDateTime.now());

        if (plano.getStatus() == null) {
            plano.setStatus(StatusPublicacao.RASCUNHO);
        }

        return planoDeGovernoRepository.save(plano);
    }

    // Retorna a busca do plano pelo Id
    @Override
    public PlanoDeGoverno buscarPorId(Long id) {
        return planoDeGovernoRepository.findById(id)
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(id));
    }

    // Retorna a busca do plano pelo Id do candidato
    @Override
    public PlanoDeGoverno buscarPorCandidatoId(Long candidatoId) {
        return planoDeGovernoRepository.findByCandidatoId(candidatoId)
                .orElseThrow(() -> new RuntimeException("Plano de governo do candidato não encontrado."));
    }

    // Retorna o plano com seus dados atualizados
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

        if (alterou){
            planoExistente.setDataAtualizacao(LocalDateTime.now());
        }

        return planoDeGovernoRepository.save(planoExistente);
    }

    // Atualiza o status do plano, entre RASCUNHO, PUBLICADO e ARQUIVADO
    @Override
    public PlanoDeGoverno atualizarStatus(Long id, StatusPublicacao status) {
        PlanoDeGoverno planoExistente = buscarPorId(id);

        if (status == null) {
            throw new CampoObrigatorioException("status");
        }

        planoExistente.setStatus(status);
        planoExistente.setDataAtualizacao(LocalDateTime.now());

        return planoDeGovernoRepository.save(planoExistente);
    }
}