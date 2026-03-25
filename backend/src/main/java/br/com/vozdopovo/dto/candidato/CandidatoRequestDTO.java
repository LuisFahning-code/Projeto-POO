package br.com.vozdopovo.dto.candidato;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CandidatoRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;
 
    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;
 
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;
 
    @NotBlank(message = "Partido é obrigatório")
    @Size(max = 100, message = "Partido deve ter no máximo 100 caracteres")
    private String partido;
 
    @NotBlank(message = "Cargo é obrigatório")
    @Size(max = 100, message = "Cargo deve ter no máximo 100 caracteres")
    private String cargo;
 
    @Size(max = 1000, message = "Biografia deve ter no máximo 1000 caracteres")
    private String biografia; // opcional
 
    public CandidatoRequestDTO() {}
 
    public CandidatoRequestDTO(String nome, String email, String senha,
                                String partido, String cargo, String biografia) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.partido = partido;
        this.cargo = cargo;
        this.biografia = biografia;
    }
 
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getPartido() { return partido; }
    public void setPartido(String partido) { this.partido = partido; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
}