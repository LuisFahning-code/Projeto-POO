package br.com.vozdopovo.exception.candidato;

import br.com.vozdopovo.exception.base.BusinessException;

public class CandidatoJaDesativadoException extends BusinessException {

    public CandidatoJaDesativadoException(Long id) {
        super("O candidato com id " + id + " já está desativado.");
    }
}
