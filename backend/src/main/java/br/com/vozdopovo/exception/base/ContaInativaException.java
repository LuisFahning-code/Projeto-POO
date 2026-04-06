package br.com.vozdopovo.exception.base;

/**
 * Exceção base para acesso negado por estado da conta ou recurso (HTTP 403).
 * Diferente do AccessDeniedException do Spring Security (que trata roles/permissões),
 * esta cobre casos de domínio onde o usuário existe e está autenticado,
 * mas não pode operar porque sua conta está inativa ou suspensa.
 */
public abstract class ContaInativaException extends RuntimeException {

    protected ContaInativaException(String message) {
        super(message);
    }
}