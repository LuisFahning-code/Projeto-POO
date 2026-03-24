package br.com.vozdopovo.exception.validation;

import br.com.vozdopovo.exception.base.ValidationException;

/**
 * Lançada quando um campo obrigatório está nulo ou em branco.
 */
public class CampoObrigatorioException extends ValidationException {

    public CampoObrigatorioException(String campo) {
        super("O campo '" + campo + "' é obrigatório e não pode ser vazio.");
    }
}
