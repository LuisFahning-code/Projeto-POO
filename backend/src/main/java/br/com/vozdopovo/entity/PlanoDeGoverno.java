package br.com.vozdopovo.entity;

import java.time.LocalDateTime;

import br.com.vozdopovo.enums.StatusPublicacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "planos_de_governo")
public class PlanoDeGoverno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String apresentacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPublicacao status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id", nullable = false, unique = true)
    private Candidato candidato;

    @Column(length = 255)
    private String nomeArquivoTxt;

    @Column(length = 500)
    private String caminhoArquivoTxt;

    @Column
    private LocalDateTime ultimaAtualizacaoTxtEm;

    public PlanoDeGoverno() {
    }

    public PlanoDeGoverno(String titulo, String apresentacao, StatusPublicacao status,
                          LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
                          Candidato candidato) {
        this.titulo = titulo;
        this.apresentacao = apresentacao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.candidato = candidato;
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

    public Candidato getCandidato() {
        return candidato;
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

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public String getNomeArquivoTxt() {
        return nomeArquivoTxt;
    }

    public void setNomeArquivoTxt(String nomeArquivoTxt) {
        this.nomeArquivoTxt = nomeArquivoTxt;
    }

    public String getCaminhoArquivoTxt() {
        return caminhoArquivoTxt;
    }

    public void setCaminhoArquivoTxt(String caminhoArquivoTxt) {
        this.caminhoArquivoTxt = caminhoArquivoTxt;
    }

    public LocalDateTime getUltimaAtualizacaoTxtEm() {
        return ultimaAtualizacaoTxtEm;
    }

    public void setUltimaAtualizacaoTxtEm(LocalDateTime ultimaAtualizacaoTxtEm) {
        this.ultimaAtualizacaoTxtEm = ultimaAtualizacaoTxtEm;
    }
}
