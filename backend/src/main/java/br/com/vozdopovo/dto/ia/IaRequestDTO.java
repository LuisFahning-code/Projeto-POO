package br.com.vozdopovo.dto.ia;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON enviado pelo backend Java para a API Python.
 * Campos alinhados com o contrato definido pela equipe de IA.
 */
public class IaRequestDTO {

    @JsonProperty("id_candidato")
    private Long idCandidato;

    @JsonProperty("caminho_txt")
    private String caminhoTxt;

    @JsonProperty("pergunta")
    private String pergunta;

    public IaRequestDTO(Long idCandidato, String caminhoTxt, String pergunta) {
        this.idCandidato = idCandidato;
        this.caminhoTxt = caminhoTxt;
        this.pergunta = pergunta;
    }

    public Long getIdCandidato() { return idCandidato; }
    public String getCaminhoTxt() { return caminhoTxt; }
    public String getPergunta() { return pergunta; }
}
