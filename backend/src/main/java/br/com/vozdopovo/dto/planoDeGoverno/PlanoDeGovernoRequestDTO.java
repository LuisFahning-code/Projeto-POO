package br.com.vozdopovo.dto.planoDeGoverno;


public class PlanoDeGovernoRequestDTO {

    private String titulo;
    private String apresentacao;
    private String nomeArquivoTxt;
    private String caminhoArquivoTxt;

    public PlanoDeGovernoRequestDTO() {
    }

    public PlanoDeGovernoRequestDTO(String titulo, String apresentacao,
                                    String nomeArquivoTxt, String caminhoArquivoTxt) {
        this.titulo = titulo;
        this.apresentacao = apresentacao;
        this.nomeArquivoTxt = nomeArquivoTxt;
        this.caminhoArquivoTxt = caminhoArquivoTxt;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public String getNomeArquivoTxt() {
        return nomeArquivoTxt;
    }

    public String getCaminhoArquivoTxt() {
        return caminhoArquivoTxt;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public void setNomeArquivoTxt(String nomeArquivoTxt) {
        this.nomeArquivoTxt = nomeArquivoTxt;
    }

    public void setCaminhoArquivoTxt(String caminhoArquivoTxt) {
        this.caminhoArquivoTxt = caminhoArquivoTxt;
    }
}
