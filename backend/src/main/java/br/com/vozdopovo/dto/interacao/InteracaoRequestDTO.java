package br.com.vozdopovo.dto.interacao;

import br.com.vozdopovo.enums.TipoInteracao;

public class InteracaoRequestDTO {

    private String titulo;
    private String conteudo;
    private TipoInteracao tipo;
    private String urlComprovacao;

    public InteracaoRequestDTO() {
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

    public String getUrlComprovacao() {
        return urlComprovacao;
    }

    public void setUrlComprovacao(String urlComprovacao) {
        this.urlComprovacao = urlComprovacao;
    }
}
