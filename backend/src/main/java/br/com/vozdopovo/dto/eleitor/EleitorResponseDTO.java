package br.com.vozdopovo.dto.eleitor;

import br.com.vozdopovo.enums.StatusConta;

public class EleitorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private StatusConta status;

    public EleitorResponseDTO() {
    }

    public EleitorResponseDTO(Long id, String nome, String email, StatusConta status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }
}
