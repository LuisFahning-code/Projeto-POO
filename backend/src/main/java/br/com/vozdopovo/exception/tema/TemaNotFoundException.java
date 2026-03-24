package br.com.vozdopovo.exception.tema;

import br.com.vozdopovo.exception.base.ResourceNotFoundException;

public class TemaNotFoundException extends ResourceNotFoundException {

    public TemaNotFoundException(Long id) {
        super("Tema não encontrado com id: " + id);
    }
}
