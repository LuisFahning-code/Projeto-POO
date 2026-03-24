package br.com.vozdopovo.exception.base;

/**
 * Exceção base para erros de validação de campo (HTTP 400).
 * Use quando um valor fornecido é inválido: campo obrigatório vazio,
 * formato incorreto, valor fora do esperado, etc.
 */
public abstract class ValidationException extends RuntimeException {

    protected ValidationException(String message) {
        super(message);
    }
}
