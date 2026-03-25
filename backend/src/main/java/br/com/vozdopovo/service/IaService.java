package br.com.vozdopovo.service;

import br.com.vozdopovo.dto.ia.PerguntaRequestDTO;
import br.com.vozdopovo.dto.ia.PerguntaResponseDTO;

public interface IaService {

    /**
     * Recebe a pergunta do usuário, busca o caminho do TXT do candidato no banco
     * e consulta a API Python, retornando a resposta ao frontend.
     */
    PerguntaResponseDTO processarPergunta(PerguntaRequestDTO request);
}
