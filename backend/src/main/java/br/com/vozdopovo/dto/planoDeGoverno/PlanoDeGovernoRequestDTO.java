package br.com.vozdopovo.dto.planoDeGoverno;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * MELHORIA #7: removidos os campos nomeArquivoTxt e caminhoArquivoTxt.
 *
 * Esses campos são gerados e gerenciados exclusivamente pelo GeradorTxtService
 * no backend. Expô-los no DTO de entrada permitia que qualquer cliente
 * sobrescrevesse o caminho do arquivo TXT no banco sem que o arquivo
 * existisse de fato, quebrando a integração com a API de IA.
 *
 * O frontend só precisa enviar título e apresentação.
 */
public class PlanoDeGovernoRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
    private String titulo;

    private String apresentacao; // opcional

    public PlanoDeGovernoRequestDTO() {}

    public PlanoDeGovernoRequestDTO(String titulo, String apresentacao) {
        this.titulo = titulo;
        this.apresentacao = apresentacao;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getApresentacao() { return apresentacao; }
    public void setApresentacao(String apresentacao) { this.apresentacao = apresentacao; }
}
