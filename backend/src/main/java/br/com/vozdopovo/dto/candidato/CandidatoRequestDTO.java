package br.com.vozdopovo.dto.candidato;

public class CandidatoRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String partido;
    private String cargo;
    private String biografia;

    public CandidatoRequestDTO() {
    }

    public CandidatoRequestDTO(String nome, String email, String senha, String partido, String cargo, String biografia) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.partido = partido;
        this.cargo = cargo;
        this.biografia = biografia;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
}