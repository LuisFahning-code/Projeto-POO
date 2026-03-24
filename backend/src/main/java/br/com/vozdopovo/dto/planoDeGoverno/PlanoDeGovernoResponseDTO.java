package br.com.vozdopovo.dto.planoDeGoverno;

import java.time.LocalDateTime;

import br.com.vozdopovo.enums.StatusPublicacao;

public class PlanoDeGovernoResponseDTO {

    private Long id;
    private String titulo;
    private String apresentacao;
    private StatusPublicacao status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long candidatoId;
    private String nomeArquivoTxt;
    private String caminhoArquivoTxt;
    private LocalDateTime ultimaAtualizacaoTxtEm;

    public PlanoDeGovernoResponseDTO() {
    }

    public PlanoDeGovernoResponseDTO(Long id, String titulo, String apresentacao, StatusPublicacao status,
                                     LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
                                     Long candidatoId, String nomeArquivoTxt, String caminhoArquivoTxt,
                                     LocalDateTime ultimaAtualizacaoTxtEm) {
        this.id = id;
        this.titulo = titulo;
        this.apresentacao = apresentacao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.candidatoId = candidatoId;
        this.nomeArquivoTxt = nomeArquivoTxt;
        this.caminhoArquivoTxt = caminhoArquivoTxt;
        this.ultimaAtualizacaoTxtEm = ultimaAtualizacaoTxtEm;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getApresentacao() {
        return apresentacao;
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

    public Long getCandidatoId() {
        return candidatoId;
    }

    public String getNomeArquivoTxt() {
        return nomeArquivoTxt;
    }

    public String getCaminhoArquivoTxt() {
        return caminhoArquivoTxt;
    }

    public LocalDateTime getUltimaAtualizacaoTxtEm() {
        return ultimaAtualizacaoTxtEm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
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

    public void setCandidatoId(Long candidatoId) {
        this.candidatoId = candidatoId;
    }

    public void setNomeArquivoTxt(String nomeArquivoTxt) {
        this.nomeArquivoTxt = nomeArquivoTxt;
    }

    public void setCaminhoArquivoTxt(String caminhoArquivoTxt) {
        this.caminhoArquivoTxt = caminhoArquivoTxt;
    }

    public void setUltimaAtualizacaoTxtEm(LocalDateTime ultimaAtualizacaoTxtEm) {
        this.ultimaAtualizacaoTxtEm = ultimaAtualizacaoTxtEm;
    }
}
