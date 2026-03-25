package br.com.vozdopovo.exception.ia;

import br.com.vozdopovo.exception.base.BusinessException;

public class IaIndisponivel extends BusinessException {

    public IaIndisponivel() {
        super("O serviço de IA está temporariamente indisponível. Tente novamente mais tarde.");
    }
}
