package br.com.vozdopovo.dto.ia;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON retornado pela API Python para o backend Java.
 */
public class IaResponseDTO {

    @JsonProperty("resposta")
    private String resposta;

    @JsonProperty("validacao_simulada")
    private String validacaoSimulada;

    public IaResponseDTO() {
    }

    public String getResposta() { return resposta; }
    public void setResposta(String resposta) { this.resposta = resposta; }
    public String getValidacaoSimulada() { return validacaoSimulada; }
    public void setValidacaoSimulada(String validacaoSimulada) { this.validacaoSimulada = validacaoSimulada; }
}
