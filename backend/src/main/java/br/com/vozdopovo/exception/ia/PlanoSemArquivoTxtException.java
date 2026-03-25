package br.com.vozdopovo.exception.ia;

import br.com.vozdopovo.exception.base.BusinessException;

public class PlanoSemArquivoTxtException extends BusinessException {

    public PlanoSemArquivoTxtException(Long candidatoId) {
        super("O candidato com id " + candidatoId + " ainda não possui arquivo TXT do plano de governo gerado.");
    }
}
