package br.com.vozdopovo.exception.candidato;

import br.com.vozdopovo.exception.base.BusinessException;

public class CandidatoEmailDuplicadoException extends BusinessException {

    public CandidatoEmailDuplicadoException(String email) {
        super("Já existe um candidato cadastrado com o e-mail: " + email);
    }
}
