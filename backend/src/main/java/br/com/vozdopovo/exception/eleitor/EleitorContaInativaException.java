package br.com.vozdopovo.exception.eleitor;
 
import br.com.vozdopovo.exception.base.ContaInativaException;
 
/**
 * Lançada quando um eleitor autenticado tenta operar com a conta inativa.
 * O usuário existe e tem token válido, mas sua conta foi desativada —
 * por isso é 403 (Forbidden) e não 404 (Not Found).
 */
public class EleitorContaInativaException extends ContaInativaException {
 
    public EleitorContaInativaException(String email) {
        super("A conta do eleitor '" + email + "' está inativa. Entre em contato com o suporte.");
    }
}
 