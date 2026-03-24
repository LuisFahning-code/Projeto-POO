package br.com.vozdopovo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.repository.EleitorRepository;
import br.com.vozdopovo.service.EleitorService;

@Service
public class EleitorServiceImpl implements EleitorService {

    private final EleitorRepository eleitorRepository;

    public EleitorServiceImpl(EleitorRepository eleitorRepository) {
        this.eleitorRepository = eleitorRepository;
    }

    // Retorna o cadastramento da conta de um novo eleitor
    @Override
    public Eleitor criar(Eleitor eleitor) {
        normalizarCampos(eleitor);
        validarCamposObrigatorios(eleitor);
        validarFormatoEmail(eleitor.getEmail());

        if (eleitorRepository.findByEmail(eleitor.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um eleitor com esse e-mail");
        }

        eleitor.setStatus(StatusConta.ATIVA);

        return eleitorRepository.save(eleitor);
    }

    // Retorna a busca de um eleitor pelo Id
    @Override
    public Eleitor buscarPorId(Long id) {
        return eleitorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleitor não encontrado"));
    }

    // Retorna a lista de todos os eleitor em que a conta tem status ATIVA
    @Override
    public List<Eleitor> listarTodosAtivos() {
        return eleitorRepository.findByStatus(StatusConta.ATIVA);
    }

    // Retorna a atualização dos dados  
    @Override
    public Eleitor atualizarDados(Long id, Eleitor eleitorAtualizado) {
        Eleitor eleitorExistente = buscarPorId(id);

        atualizarCampoNome(eleitorExistente, eleitorAtualizado);
        atualizarCampoEmail(eleitorExistente, eleitorAtualizado);
        atualizarCampoSenha(eleitorExistente, eleitorAtualizado);

        return eleitorRepository.save(eleitorExistente);
    }

    // Torna a conta com status DESATIVADA
    @Override
    public void desativar(Long id) {
        Eleitor eleitor = buscarPorId(id);

        if (eleitor.getStatus() == StatusConta.DESATIVADA) {
            throw new RuntimeException("Eleitor já está desativado");
        }

        eleitor.setStatus(StatusConta.DESATIVADA);
        eleitorRepository.save(eleitor);
    }

    // =============================
    //  MÉTODOS AUXILIARES
    // ============================= 

    // Verificação do preenchimento dos campos
    private void normalizarCampos(Eleitor eleitor) {
        if (eleitor.getNome() != null) {
            eleitor.setNome(eleitor.getNome().trim());
        }

        if (eleitor.getEmail() != null) {
            eleitor.setEmail(eleitor.getEmail().trim());
        }
    }

    // Validação de campos que devem ter preenchimento obrigatório
    private void validarCamposObrigatorios(Eleitor eleitor) {
        if (eleitor.getNome() == null || eleitor.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (eleitor.getEmail() == null || eleitor.getEmail().isBlank()) {
            throw new RuntimeException("E-mail é obrigatório");
        }

        if (eleitor.getSenha() == null || eleitor.getSenha().isBlank()) {
            throw new RuntimeException("Senha é obrigatória");
        }
    }

    // Validação do formato do email eleitor
    private void validarFormatoEmail(String email) {
        if (!email.contains("@")) {
            throw new RuntimeException("Formato de e-mail inválido");
        }
    }

    // Atualização, com verificação, do nome do eleitor
    private void atualizarCampoNome(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getNome() != null) {
            String nome = atualizado.getNome().trim();

            if (nome.isBlank()) {
                throw new RuntimeException("Nome não pode ser vazio");
            }

            existente.setNome(nome);
        }
    }

    // Atualização, com verificação, do email do eleitor
    private void atualizarCampoEmail(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getEmail() != null) {
            String email = atualizado.getEmail().trim();

            if (email.isBlank()) {
                throw new RuntimeException("E-mail não pode ser vazio");
            }

            validarFormatoEmail(email);

            if (!existente.getEmail().equals(email)
                    && eleitorRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException("Já existe um eleitor com esse e-mail");
            }

            existente.setEmail(email);
        }
    }

    // Atualização, com verificação, da senha do eleitor
    private void atualizarCampoSenha(Eleitor existente, Eleitor atualizado) {
        if (atualizado.getSenha() != null) {
            String senha = atualizado.getSenha().trim();

            if (senha.isBlank()) {
                throw new RuntimeException("Senha não pode ser vazia");
            }

            existente.setSenha(senha);
        }
    }
}
