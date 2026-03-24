package br.com.vozdopovo.dto.tema;

import java.time.LocalDateTime;

import br.com.vozdopovo.enums.StatusPublicacao;

public class TemaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private StatusPublicacao status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long planoDeGovernoId;

    public TemaResponseDTO() {
    }

    public TemaResponseDTO(Long id, String titulo, String descricao, StatusPublicacao status,
                           LocalDateTime dataCriacao, LocalDateTime dataAtualizacao, Long planoDeGovernoId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.planoDeGovernoId = planoDeGovernoId;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
}