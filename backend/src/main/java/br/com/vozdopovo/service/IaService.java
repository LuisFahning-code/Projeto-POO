package br.com.vozdopovo.service;

import br.com.vozdopovo.dto.ia.PerguntaRequestDTO;
import br.com.vozdopovo.dto.ia.PerguntaResponseDTO;

public interface IaService {

    /**
     * Recebe a pergunta do eleitor, valida que o eleitor está ativo,
     * busca o caminho do TXT do candidato no banco e consulta a API Python,
     * retornando a resposta ao frontend.
     *
     * MELHORIA #8: emailEleitor adicionado para validar que o requisitante
     * é um eleitor com conta ativa antes de consumir a API de IA.
     *
     * @param request      dados da pergunta (candidatoId + texto da pergunta)
     * @param emailEleitor email extraído do token JWT pelo controller
     */
    PerguntaResponseDTO processarPergunta(PerguntaRequestDTO request, String emailEleitor);
}
