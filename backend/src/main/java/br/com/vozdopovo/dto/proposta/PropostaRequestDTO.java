package br.com.vozdopovo.dto.proposta;

public class PropostaRequestDTO {

    private String titulo;
    private String resumo;
    private String detalhamento;

    public PropostaRequestDTO() {
    }

    public PropostaRequestDTO(String titulo, String resumo, String detalhamento) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.detalhamento = detalhamento;
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public void setDetalhamento(String detalhamento) {
        this.detalhamento = detalhamento;
    }
}
