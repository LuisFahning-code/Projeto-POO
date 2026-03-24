package br.com.vozdopovo.exception.eleitor;

import br.com.vozdopovo.exception.base.ResourceNotFoundException;

public class EleitorNotFoundException extends ResourceNotFoundException {

    public EleitorNotFoundException(Long id) {
        super("Eleitor não encontrado com id: " + id);
    }
}
