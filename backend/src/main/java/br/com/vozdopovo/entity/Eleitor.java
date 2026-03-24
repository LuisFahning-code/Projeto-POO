package br.com.vozdopovo.entity;

import br.com.vozdopovo.enums.StatusConta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "eleitores")

public class Eleitor extends Usuario {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusConta status;

    public Eleitor() {
    }

    public Eleitor(String nome, String email, String senha) {
        super(nome, email, senha);
        this.status = StatusConta.ATIVA;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }
}
