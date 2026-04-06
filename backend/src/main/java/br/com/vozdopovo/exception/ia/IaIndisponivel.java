package br.com.vozdopovo.exception.ia;
 
import br.com.vozdopovo.exception.base.ServiceUnavailableException;
 
/**
 * Lançada quando a API Python de IA não responde ou retorna null.
 * Mapeada para HTTP 503 Service Unavailable no GlobalExceptionHandler.
 */
public class IaIndisponivel extends ServiceUnavailableException {
 
    public IaIndisponivel() {
        super("O serviço de IA está temporariamente indisponível. Tente novamente mais tarde.");
    }
}
