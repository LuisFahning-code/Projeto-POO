package br.com.vozdopovo.dto.candidato;

import br.com.vozdopovo.enums.StatusConta;

public class CandidatoResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String partido;
    private String cargo;
    private String biografia;
    private StatusConta status;

    public CandidatoResponseDTO() {
    }

    public CandidatoResponseDTO(Long id, String nome, String email, String partido, String cargo, String biografia, StatusConta status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.partido = partido;
        this.cargo = cargo;
        this.biografia = biografia;
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

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }
}
