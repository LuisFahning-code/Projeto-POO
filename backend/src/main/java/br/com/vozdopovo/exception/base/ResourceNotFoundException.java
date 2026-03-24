package br.com.vozdopovo.exception.base;

/**
 * Exceção base para recursos não encontrados (HTTP 404).
 * Todas as exceções de "not found" por domínio devem estender esta classe.
 */
public abstract class ResourceNotFoundException extends RuntimeException {

    protected ResourceNotFoundException(String message) {
        super(message);
    }
}
