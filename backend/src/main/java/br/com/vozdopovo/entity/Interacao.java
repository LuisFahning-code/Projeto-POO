package br.com.vozdopovo.entity;

import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;
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
@Table(name = "interacoes")
public class Interacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoInteracao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusInteracao status;

    @Column(length = 500)
    private String urlComprovacao;

    @Column(columnDefinition = "TEXT")
    private String resposta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eleitor_id", nullable = false)
    private Eleitor eleitor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    public Interacao() {
    }

    public Interacao(String titulo, String conteudo, TipoInteracao tipo,
                     StatusInteracao status, String urlComprovacao,
                     String resposta, Eleitor eleitor, Candidato candidato) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.tipo = tipo;
        this.status = status;
        this.urlComprovacao = urlComprovacao;
        this.resposta = resposta;
        this.eleitor = eleitor;
        this.candidato = candidato;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public TipoInteracao getTipo() {
        return tipo;
    }

    public StatusInteracao getStatus() {
        return status;
    }

    public String getUrlComprovacao() {
        return urlComprovacao;
    }

    public String getResposta() {
        return resposta;
    }

    public Eleitor getEleitor() {
        return eleitor;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setTipo(TipoInteracao tipo) {
        this.tipo = tipo;
    }

    public void setStatus(StatusInteracao status) {
        this.status = status;
    }

    public void setUrlComprovacao(String urlComprovacao) {
        this.urlComprovacao = urlComprovacao;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public void setEleitor(Eleitor eleitor) {
        this.eleitor = eleitor;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
}
