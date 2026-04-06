package br.com.vozdopovo.exception.ia;
 
import br.com.vozdopovo.exception.base.ResourceNotFoundException;
 
/**
 * Lançada quando o plano de governo existe mas ainda não teve seu TXT gerado.
 * O recurso (arquivo TXT) não está disponível — HTTP 404 é semanticamente correto.
 */
public class PlanoSemArquivoTxtException extends ResourceNotFoundException {
 
    public PlanoSemArquivoTxtException(Long candidatoId) {
        super("O candidato com id " + candidatoId + " ainda não possui arquivo TXT do plano de governo gerado.");
    }
}
