package br.com.vozdopovo.exception.eleitor;

import br.com.vozdopovo.exception.base.BusinessException;

public class EleitorEmailDuplicadoException extends BusinessException {

    public EleitorEmailDuplicadoException(String email) {
        super("Já existe um eleitor cadastrado com o e-mail: " + email);
    }
}
