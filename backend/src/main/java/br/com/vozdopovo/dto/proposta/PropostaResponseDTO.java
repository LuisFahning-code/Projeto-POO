package br.com.vozdopovo.dto.proposta;

import java.time.LocalDateTime;

import br.com.vozdopovo.enums.StatusPublicacao;

public class PropostaResponseDTO {

    private Long id;
    private String titulo;
    private String resumo;
    private String detalhamento;
    private StatusPublicacao status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long planoDeGovernoId;
    private Long temaId;

    public PropostaResponseDTO() {
    }

    public PropostaResponseDTO(Long id, String titulo, String resumo, String detalhamento,
                               StatusPublicacao status, LocalDateTime dataCriacao,
                               LocalDateTime dataAtualizacao, Long planoDeGovernoId, Long temaId) {
        this.id = id;
        this.titulo = titulo;
        this.resumo = resumo;
        this.detalhamento = detalhamento;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.planoDeGovernoId = planoDeGovernoId;
        this.temaId = temaId;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public StatusPublicacao getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public Long getPlanoDeGovernoId() {
        return planoDeGovernoId;
    }

    public Long getTemaId() {
        return temaId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public void setDetalhamento(String detalhamento) {
        this.detalhamento = detalhamento;
    }

    public void setStatus(StatusPublicacao status) {
        this.status = status;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setPlanoDeGovernoId(Long planoDeGovernoId) {
        this.planoDeGovernoId = planoDeGovernoId;
    }

    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }
}
