package br.com.vozdopovo.entity;

import br.com.vozdopovo.enums.TipoArquivo;
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
@Table(name = "anexos_proposta")
public class AnexoProposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nomeArquivo;

    @Column(nullable = false, length = 500)
    private String urlArquivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoArquivo tipoArquivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposta_id", nullable = false)
    private Proposta proposta;

    public AnexoProposta() {
    }

    public AnexoProposta(String nomeArquivo, String urlArquivo, TipoArquivo tipoArquivo, Proposta proposta) {
        this.nomeArquivo = nomeArquivo;
        this.urlArquivo = urlArquivo;
        this.tipoArquivo = tipoArquivo;
        this.proposta = proposta;
    }

    public Long getId() {
        return id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public String getUrlArquivo() {
        return urlArquivo;
    }

    public TipoArquivo getTipoArquivo() {
        return tipoArquivo;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public void setTipoArquivo(TipoArquivo tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }
}
