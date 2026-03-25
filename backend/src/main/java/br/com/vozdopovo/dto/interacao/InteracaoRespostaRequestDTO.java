package br.com.vozdopovo.dto.interacao;
 
import jakarta.validation.constraints.NotBlank;
 
public class InteracaoRespostaRequestDTO {
 
    @NotBlank(message = "Resposta é obrigatória")
    private String resposta;
 
    public InteracaoRespostaRequestDTO() {}
 
    public String getResposta() { return resposta; }
    public void setResposta(String resposta) { this.resposta = resposta; }
}
 
