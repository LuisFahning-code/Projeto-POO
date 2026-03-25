package br.com.vozdopovo.dto.ia;

/**
 * JSON devolvido pelo backend Java ao frontend após consulta à IA.
 */
public class PerguntaResponseDTO {

    private String resposta;
    private String validacaoSimulada;

    public PerguntaResponseDTO(String resposta, String validacaoSimulada) {
        this.resposta = resposta;
        this.validacaoSimulada = validacaoSimulada;
    }

    public String getResposta() { return resposta; }
    public String getValidacaoSimulada() { return validacaoSimulada; }
}
