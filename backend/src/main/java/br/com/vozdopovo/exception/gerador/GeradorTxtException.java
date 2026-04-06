package br.com.vozdopovo.exception.gerador;

import br.com.vozdopovo.exception.base.ServiceUnavailableException;

/**
 * Lançada quando o GeradorTxtService falha ao gravar o arquivo TXT do plano.
 * Mapeada para HTTP 503 Service Unavailable no GlobalExceptionHandler.
 */
public class GeradorTxtException extends ServiceUnavailableException {

    public GeradorTxtException(Long candidatoId, String causa) {
        super("Não foi possível gerar o arquivo TXT do plano do candidato " +
              candidatoId + ": " + causa);
    }
}
