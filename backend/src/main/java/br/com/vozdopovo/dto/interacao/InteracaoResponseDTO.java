package br.com.vozdopovo.dto.interacao;

import java.time.LocalDateTime;

import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;

public class InteracaoResponseDTO {

    private Long id;
    private String titulo;
    private String conteudo;
    private TipoInteracao tipo;
    private StatusInteracao status;
    private String urlComprovacao;
    private String resposta;
    private Long eleitorId;
    private Long candidatoId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataResposta;

    public InteracaoResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public TipoInteracao getTipo() {
        return tipo;
    }

    public void setTipo(TipoInteracao tipo) {
        this.tipo = tipo;
    }

    public StatusInteracao getStatus() {
        return status;
    }

    public void setStatus(StatusInteracao status) {
        this.status = status;
    }

    public String getUrlComprovacao() {
        return urlComprovacao;
    }

    public void setUrlComprovacao(String urlComprovacao) {
        this.urlComprovacao = urlComprovacao;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Long getEleitorId() {
        return eleitorId;
    }

    public void setEleitorId(Long eleitorId) {
        this.eleitorId = eleitorId;
    }

    public Long getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(Long candidatoId) {
        this.candidatoId = candidatoId;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(LocalDateTime dataResposta) {
        this.dataResposta = dataResposta;
    }
}
