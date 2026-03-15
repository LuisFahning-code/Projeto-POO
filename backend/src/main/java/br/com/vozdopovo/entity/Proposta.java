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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "propostas")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 300)
    private String resumo;

    @Column(columnDefinition = "TEXT")
    private String detalhamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPublicacao status;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoDeGoverno planoDeGoverno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tema_id", nullable = false)
    private Tema tema;

    public Proposta() {
    }

    public Proposta(String titulo, String resumo, String detalhamento,
                    StatusPublicacao status, LocalDateTime dataCriacao,
                    LocalDateTime dataAtualizacao, PlanoDeGoverno planoDeGoverno,
                    Tema tema) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.detalhamento = detalhamento;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.planoDeGoverno = planoDeGoverno;
        this.tema = tema;
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

    public PlanoDeGoverno getPlanoDeGoverno() {
        return planoDeGoverno;
    }

    public Tema getTema() {
        return tema;
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

    public void setPlanoDeGoverno(PlanoDeGoverno planoDeGoverno) {
        this.planoDeGoverno = planoDeGoverno;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
