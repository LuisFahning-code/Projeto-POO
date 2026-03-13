package br.com.vozdopovo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "candidatos")
public class Candidato extends Usuario {

    private String partido;
    private String cargo;
    private String biografia;

    public Candidato() {}
    
    public Candidato(String nome, String email, String senha, 
                     String partido, String cargo, String biografia) {
        super(nome, email, senha);
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
}
