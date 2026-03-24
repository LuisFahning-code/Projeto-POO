package br.com.vozdopovo.exception.interacao;

import br.com.vozdopovo.exception.base.BusinessException;
import br.com.vozdopovo.enums.StatusInteracao;

public class InteracaoNaoPermiteRespostaException extends BusinessException {

    public InteracaoNaoPermiteRespostaException(Long id, StatusInteracao status) {
        super("A interação com id " + id + " não permite resposta pois está com status: " + status.name());
    }
}
