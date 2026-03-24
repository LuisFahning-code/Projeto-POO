package br.com.vozdopovo.exception.eleitor;

import br.com.vozdopovo.exception.base.BusinessException;

public class EleitorJaDesativadoException extends BusinessException {

    public EleitorJaDesativadoException(Long id) {
        super("O eleitor com id " + id + " já está desativado.");
    }
}
