package br.com.vozdopovo.entity;

import br.com.vozdopovo.enums.StatusConta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidatos")
public class Candidato extends Usuario {

    private String partido;
    private String cargo;
    private String biografia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConta status;

    public Candidato() {}
    
    public Candidato(String nome, String email, String senha, String status, 
                     String partido, String cargo, String biografia) {
        super(nome, email, senha);
        this.status = StatusConta.ATIVA;
        this.partido = partido;
        this.cargo = cargo;
        this.biografia = biografia;
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
