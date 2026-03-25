package br.com.vozdopovo.dto.ia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * JSON recebido pelo backend Java vindo do frontend.
 * O frontend envia apenas o candidatoId e a pergunta —
 * o backend resolve o caminho do TXT consultando o banco.
 */
public class PerguntaRequestDTO {

    @NotNull(message = "O id do candidato é obrigatório")
    private Long candidatoId;

    @NotBlank(message = "A pergunta é obrigatória")
    private String pergunta;

    public PerguntaRequestDTO() {
    }

    public Long getCandidatoId() { return candidatoId; }
    public void setCandidatoId(Long candidatoId) { this.candidatoId = candidatoId; }
    public String getPergunta() { return pergunta; }
    public void setPergunta(String pergunta) { this.pergunta = pergunta; }
}
