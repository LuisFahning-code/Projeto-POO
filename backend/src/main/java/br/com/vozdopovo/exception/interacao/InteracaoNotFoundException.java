package br.com.vozdopovo.exception.interacao;

import br.com.vozdopovo.exception.base.ResourceNotFoundException;

public class InteracaoNotFoundException extends ResourceNotFoundException {

    public InteracaoNotFoundException(Long id) {
        super("Interação não encontrada com id: " + id);
    }
}
