package br.com.vozdopovo.exception.eleitor;

import br.com.vozdopovo.exception.base.ResourceNotFoundException;

public class EleitorNotFoundException extends ResourceNotFoundException {

    public EleitorNotFoundException(Long id) {
        super("Eleitor não encontrado com id: " + id);
    }

    // MELHORIA #8: construtor por email, usado no IaServiceImpl para
    // validar o eleitor autenticado sem expor o id interno na mensagem.
    public EleitorNotFoundException(String email) {
        super("Eleitor não encontrado com e-mail: " + email);
    }
}
