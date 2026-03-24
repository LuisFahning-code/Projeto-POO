package br.com.vozdopovo.exception.base;

/**
 * Exceção base para violações de regra de negócio (HTTP 422).
 * Use quando a requisição é válida sintaticamente, mas viola uma regra do domínio.
 * Exemplos: e-mail duplicado, conta já desativada, status inválido para a operação.
 */
public abstract class BusinessException extends RuntimeException {

    protected BusinessException(String message) {
        super(message);
    }
}
