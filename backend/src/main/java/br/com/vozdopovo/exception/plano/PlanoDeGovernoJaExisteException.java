package br.com.vozdopovo.exception.plano;

import br.com.vozdopovo.exception.base.BusinessException;

public class PlanoDeGovernoJaExisteException extends BusinessException {

    public PlanoDeGovernoJaExisteException(Long candidatoId) {
        super("O candidato com id " + candidatoId + " já possui um plano de governo cadastrado.");
    }
}
