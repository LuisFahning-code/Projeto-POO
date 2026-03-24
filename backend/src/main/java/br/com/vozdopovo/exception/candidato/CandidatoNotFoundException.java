package br.com.vozdopovo.exception.candidato;

import br.com.vozdopovo.exception.base.ResourceNotFoundException;

public class CandidatoNotFoundException extends ResourceNotFoundException {

    public CandidatoNotFoundException(Long id) {
        super("Candidato não encontrado com id: " + id);
    }
}
