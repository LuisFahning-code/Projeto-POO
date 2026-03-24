package br.com.vozdopovo.exception.plano;

import br.com.vozdopovo.exception.base.ResourceNotFoundException;

public class PlanoDeGovernoNotFoundException extends ResourceNotFoundException {

    public PlanoDeGovernoNotFoundException(Long id) {
        super("Plano de governo não encontrado com id: " + id);
    }
}
