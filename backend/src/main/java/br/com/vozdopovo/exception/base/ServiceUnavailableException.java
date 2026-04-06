package br.com.vozdopovo.exception.base;
 
/**
 * Exceção base para serviços externos indisponíveis (HTTP 503).
 * Use quando uma dependência externa (ex.: API de IA) não responde
 * e o sistema não pode completar a operação no momento.
 */
public abstract class ServiceUnavailableException extends RuntimeException {
 
    protected ServiceUnavailableException(String message) {
        super(message);
    }
}
 
