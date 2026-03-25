package br.com.vozdopovo.dto.proposta;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
 
public class PropostaRequestDTO {
 
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
    private String titulo;
 
    @NotBlank(message = "Resumo é obrigatório")
    @Size(max = 300, message = "Resumo deve ter no máximo 300 caracteres")
    private String resumo;
 
    private String detalhamento; // opcional
 
    public PropostaRequestDTO() {}
 
    public PropostaRequestDTO(String titulo, String resumo, String detalhamento) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.detalhamento = detalhamento;
    }
 
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }
    public String getDetalhamento() { return detalhamento; }
    public void setDetalhamento(String detalhamento) { this.detalhamento = detalhamento; }
}
 
