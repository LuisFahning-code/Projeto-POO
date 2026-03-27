package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vozdopovo.dto.candidato.CandidatoRequestDTO;
import br.com.vozdopovo.dto.candidato.CandidatoResponseDTO;
import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.mapper.CandidatoMapper;
import br.com.vozdopovo.service.CandidatoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    private final CandidatoService candidatoService;

    public CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    @PostMapping
    public ResponseEntity<CandidatoResponseDTO> criar(@RequestBody @Valid CandidatoRequestDTO dto) {
        Candidato candidato = CandidatoMapper.toEntity(dto);
        Candidato novoCandidato = candidatoService.criar(candidato);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CandidatoMapper.toResponseDTO(novoCandidato));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> buscarPorId(@PathVariable Long id) {
        // CORREÇÃO: usa buscarPublicoPorId — retorna 404 para candidatos inativos
        Candidato candidato = candidatoService.buscarPublicoPorId(id);
        return ResponseEntity.ok(CandidatoMapper.toResponseDTO(candidato));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CandidatoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        List<Candidato> candidatos = candidatoService.buscarPorNome(nome);
        List<CandidatoResponseDTO> response = candidatos.stream()
                .map(CandidatoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<CandidatoResponseDTO>> listarTodosAtivos() {
        List<Candidato> candidatos = candidatoService.listarTodosAtivos();
        List<CandidatoResponseDTO> response = candidatos.stream()
                .map(CandidatoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * CORREÇÃO #3: verifica que o candidato autenticado é o dono do recurso antes
     * de permitir a atualização. Um eleitor ou outro candidato com token válido
     * não pode alterar dados de outro candidato.
     *
     * O principal do token JWT é o email do usuário autenticado (setado pelo JwtFilter).
     */
    @PutMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> atualizarDados(
            @PathVariable Long id,
            @RequestBody @Valid CandidatoRequestDTO dto,
            Authentication authentication) {

        validarPropriedade(id, authentication);

        Candidato candidatoAtualizado = CandidatoMapper.toEntity(dto);
        Candidato candidato = candidatoService.atualizarDados(id, candidatoAtualizado);
        return ResponseEntity.ok(CandidatoMapper.toResponseDTO(candidato));
    }

    /**
     * CORREÇÃO #3: mesma verificação de propriedade na desativação.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(
            @PathVariable Long id,
            Authentication authentication) {

        validarPropriedade(id, authentication);

        candidatoService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Verifica que o email do token corresponde ao email do candidato alvo.
     * Lança AccessDeniedException (→ HTTP 403) se não corresponder,
     * tratado pelo GlobalExceptionHandler.
     */
    private void validarPropriedade(Long candidatoId, Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        Candidato candidato = candidatoService.buscarPorId(candidatoId);

        if (!candidato.getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para modificar dados de outro candidato.");
        }
    }
}
