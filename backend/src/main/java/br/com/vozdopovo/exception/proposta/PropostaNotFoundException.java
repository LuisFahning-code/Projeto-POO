package br.com.vozdopovo.exception.proposta;

import br.com.vozdopovo.exception.base.ResourceNotFoundException;

public class PropostaNotFoundException extends ResourceNotFoundException {

    public PropostaNotFoundException(Long id) {
        super("Proposta não encontrada com id: " + id);
    }
}
