package br.com.vozdopovo.exception.validation;

import br.com.vozdopovo.exception.base.ValidationException;

/**
 * Lançada quando um campo tem valor presente, mas com formato inválido.
 * Exemplos: e-mail sem @, URL malformada, data fora do padrão.
 */
public class FormatoInvalidoException extends ValidationException {

    public FormatoInvalidoException(String campo, String detalhe) {
        super("O campo '" + campo + "' possui formato inválido: " + detalhe);
    }
}
