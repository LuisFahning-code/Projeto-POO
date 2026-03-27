package br.com.vozdopovo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vozdopovo.dto.eleitor.EleitorRequestDTO;
import br.com.vozdopovo.dto.eleitor.EleitorResponseDTO;
import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.mapper.EleitorMapper;
import br.com.vozdopovo.service.EleitorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/eleitores")
public class EleitorController {

    private final EleitorService eleitorService;

    public EleitorController(EleitorService eleitorService) {
        this.eleitorService = eleitorService;
    }

    @PostMapping
    public ResponseEntity<EleitorResponseDTO> criar(@RequestBody @Valid EleitorRequestDTO dto) {
        Eleitor eleitor = EleitorMapper.toEntity(dto);
        Eleitor novoEleitor = eleitorService.criar(eleitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(EleitorMapper.toResponseDTO(novoEleitor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EleitorResponseDTO> buscarPorId(@PathVariable Long id) {
        Eleitor eleitor = eleitorService.buscarPorId(id);
        return ResponseEntity.ok(EleitorMapper.toResponseDTO(eleitor));
    }

    @GetMapping
    public ResponseEntity<List<EleitorResponseDTO>> listarTodosAtivos() {
        List<EleitorResponseDTO> eleitores = eleitorService.listarTodosAtivos()
                .stream()
                .map(EleitorMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eleitores);
    }

    /**
     * CORREÇÃO #3: verifica que o eleitor autenticado é o dono do recurso antes
     * de permitir a atualização. Um candidato ou outro eleitor com token válido
     * não pode alterar dados de outro eleitor.
     *
     * O principal do token JWT é o email do usuário autenticado (setado pelo JwtFilter).
     */
    @PutMapping("/{id}")
    public ResponseEntity<EleitorResponseDTO> atualizarDados(
            @PathVariable Long id,
            @RequestBody @Valid EleitorRequestDTO dto,
            Authentication authentication) {

        validarPropriedade(id, authentication);

        Eleitor eleitorAtualizado = EleitorMapper.toEntity(dto);
        Eleitor eleitor = eleitorService.atualizarDados(id, eleitorAtualizado);
        return ResponseEntity.ok(EleitorMapper.toResponseDTO(eleitor));
    }

    /**
     * CORREÇÃO #3: mesma verificação de propriedade na desativação.
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(
            @PathVariable Long id,
            Authentication authentication) {

        validarPropriedade(id, authentication);

        eleitorService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Verifica que o email do token corresponde ao email do eleitor alvo.
     * Lança AccessDeniedException (→ HTTP 403) se não corresponder,
     * tratado pelo GlobalExceptionHandler.
     */
    private void validarPropriedade(Long eleitorId, Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        Eleitor eleitor = eleitorService.buscarPorId(eleitorId);

        if (!eleitor.getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para modificar dados de outro eleitor.");
        }
    }
}
