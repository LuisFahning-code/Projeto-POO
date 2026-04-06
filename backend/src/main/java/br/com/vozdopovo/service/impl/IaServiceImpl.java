package br.com.vozdopovo.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.vozdopovo.dto.ia.IaRequestDTO;
import br.com.vozdopovo.dto.ia.IaResponseDTO;
import br.com.vozdopovo.dto.ia.PerguntaRequestDTO;
import br.com.vozdopovo.dto.ia.PerguntaResponseDTO;
import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.exception.eleitor.EleitorContaInativaException;
import br.com.vozdopovo.exception.eleitor.EleitorNotFoundException;
import br.com.vozdopovo.exception.ia.IaIndisponivel;
import br.com.vozdopovo.exception.ia.PlanoSemArquivoTxtException;
import br.com.vozdopovo.exception.plano.PlanoDeGovernoNotFoundException;
import br.com.vozdopovo.repository.EleitorRepository;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.service.IaService;

@Service
public class IaServiceImpl implements IaService {

    private final PlanoDeGovernoRepository planoDeGovernoRepository;
    private final EleitorRepository eleitorRepository;
    private final RestTemplate restTemplate;

    @Value("${ia.api.url}")
    private String iaApiUrl;

    public IaServiceImpl(PlanoDeGovernoRepository planoDeGovernoRepository,
                         EleitorRepository eleitorRepository,
                         RestTemplate restTemplate) {
        this.planoDeGovernoRepository = planoDeGovernoRepository;
        this.eleitorRepository = eleitorRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public PerguntaResponseDTO processarPergunta(PerguntaRequestDTO request, String emailEleitor) {

        // Valida que o eleitor autenticado existe no banco
        Eleitor eleitor = eleitorRepository.findByEmail(emailEleitor)
                .orElseThrow(() -> new EleitorNotFoundException(emailEleitor));

        // Eleitor existe mas conta está inativa — 403, não 404
        if (eleitor.getStatus() != StatusConta.ATIVA) {
            throw new EleitorContaInativaException(emailEleitor);
        }

        // Busca o plano do candidato no banco
        PlanoDeGoverno plano = planoDeGovernoRepository
                .findByCandidatoId(request.getCandidatoId())
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(request.getCandidatoId()));

        // Plano existe mas não está publicado — eleitor não pode consultá-lo
        if (plano.getStatus() != StatusPublicacao.PUBLICADO) {
            throw new PlanoDeGovernoNotFoundException(request.getCandidatoId());
        }

        // TXT ainda não foi gerado para este plano
        if (plano.getCaminhoArquivoTxt() == null || plano.getCaminhoArquivoTxt().isBlank()) {
            throw new PlanoSemArquivoTxtException(request.getCandidatoId());
        }

        // Monta o payload para a API Python com o caminho já resolvido pelo backend
        IaRequestDTO iaRequest = new IaRequestDTO(
                request.getCandidatoId(),
                plano.getCaminhoArquivoTxt(),
                request.getPergunta()
        );

        // Chama a API Python e retorna a resposta ao frontend
        try {
            IaResponseDTO iaResponse = restTemplate.postForObject(
                    iaApiUrl,
                    iaRequest,
                    IaResponseDTO.class
            );

            if (iaResponse == null) {
                throw new IaIndisponivel();
            }

            return new PerguntaResponseDTO(
                    iaResponse.getResposta(),
                    iaResponse.getValidacaoSimulada()
            );

        } catch (RestClientException e) {
            throw new IaIndisponivel();
        }
    }
}