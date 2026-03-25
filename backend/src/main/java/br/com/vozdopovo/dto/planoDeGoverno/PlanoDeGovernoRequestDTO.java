package br.com.vozdopovo.dto.planoDeGoverno;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
 
public class PlanoDeGovernoRequestDTO {
 
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
    private String titulo;
 
    private String apresentacao; // opcional
 
    @Size(max = 255, message = "Nome do arquivo deve ter no máximo 255 caracteres")
    private String nomeArquivoTxt; // opcional
 
    @Size(max = 500, message = "Caminho do arquivo deve ter no máximo 500 caracteres")
    private String caminhoArquivoTxt; // opcional
 
    public PlanoDeGovernoRequestDTO() {}
 
    public PlanoDeGovernoRequestDTO(String titulo, String apresentacao,
                                    String nomeArquivoTxt, String caminhoArquivoTxt) {
        this.titulo = titulo;
        this.apresentacao = apresentacao;
        this.nomeArquivoTxt = nomeArquivoTxt;
        this.caminhoArquivoTxt = caminhoArquivoTxt;
    }
 
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getApresentacao() { return apresentacao; }
    public void setApresentacao(String apresentacao) { this.apresentacao = apresentacao; }
    public String getNomeArquivoTxt() { return nomeArquivoTxt; }
    public void setNomeArquivoTxt(String nomeArquivoTxt) { this.nomeArquivoTxt = nomeArquivoTxt; }
    public String getCaminhoArquivoTxt() { return caminhoArquivoTxt; }
    public void setCaminhoArquivoTxt(String caminhoArquivoTxt) { this.caminhoArquivoTxt = caminhoArquivoTxt; }
}
