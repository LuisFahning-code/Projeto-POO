package br.com.vozdopovo.dto.tema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TemaRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
    private String titulo;

    private String descricao; // opcional

    public TemaRequestDTO() {}

    public TemaRequestDTO(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}