package br.com.vozdopovo.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.vozdopovo.dto.ia.IaRequestDTO;
import br.com.vozdopovo.dto.ia.IaResponseDTO;
import br.com.vozdopovo.dto.ia.PerguntaRequestDTO;
import br.com.vozdopovo.dto.ia.PerguntaResponseDTO;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.exception.ia.IaIndisponivel;
import br.com.vozdopovo.exception.ia.PlanoSemArquivoTxtException;
import br.com.vozdopovo.exception.plano.PlanoDeGovernoNotFoundException;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.service.IaService;

@Service
public class IaServiceImpl implements IaService {

    private final PlanoDeGovernoRepository planoDeGovernoRepository;
    private final RestTemplate restTemplate;

    @Value("${ia.api.url}")
    private String iaApiUrl;

    public IaServiceImpl(PlanoDeGovernoRepository planoDeGovernoRepository,
                         RestTemplate restTemplate) {
        this.planoDeGovernoRepository = planoDeGovernoRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public PerguntaResponseDTO processarPergunta(PerguntaRequestDTO request) {

        // Busca o plano do candidato no banco
        PlanoDeGoverno plano = planoDeGovernoRepository
                .findByCandidatoId(request.getCandidatoId())
                .orElseThrow(() -> new PlanoDeGovernoNotFoundException(request.getCandidatoId()));

        // Verifica se o TXT já foi gerado
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
