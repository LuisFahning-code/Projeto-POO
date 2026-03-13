package br.com.vozdopovo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "eleitores")

public class Eleitor extends Usuario {

    public Eleitor() {
    }

    public Eleitor(String nome, String email, String senha) {
        super(nome, email, senha);
    }
}
