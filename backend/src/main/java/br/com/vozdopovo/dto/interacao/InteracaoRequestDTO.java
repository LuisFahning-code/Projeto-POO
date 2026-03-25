package br.com.vozdopovo.dto.interacao;
 
import br.com.vozdopovo.enums.TipoInteracao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
public class InteracaoRequestDTO {
 
    @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
    private String titulo; // opcional
 
    @NotBlank(message = "Conteúdo é obrigatório")
    private String conteudo;
 
    @NotNull(message = "Tipo da interação é obrigatório")
    private TipoInteracao tipo;
 
    @Size(max = 500, message = "URL de comprovação deve ter no máximo 500 caracteres")
    private String urlComprovacao; // opcional
 
    public InteracaoRequestDTO() {}
 
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public TipoInteracao getTipo() { return tipo; }
    public void setTipo(TipoInteracao tipo) { this.tipo = tipo; }
    public String getUrlComprovacao() { return urlComprovacao; }
    public void setUrlComprovacao(String urlComprovacao) { this.urlComprovacao = urlComprovacao; }
}