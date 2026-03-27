package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import br.com.vozdopovo.dto.tema.TemaRequestDTO;
import br.com.vozdopovo.dto.tema.TemaResponseDTO;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.mapper.TemaMapper;
import br.com.vozdopovo.service.PlanoDeGovernoService;
import br.com.vozdopovo.service.TemaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/temas")
public class TemaController {

    private final TemaService temaService;
    private final PlanoDeGovernoService planoDeGovernoService;

    public TemaController(TemaService temaService,
                          PlanoDeGovernoService planoDeGovernoService) {
        this.temaService = temaService;
        this.planoDeGovernoService = planoDeGovernoService;
    }

    // -------------------------------------------------------
    // ROTAS PÚBLICAS — sem autenticação, apenas conteúdo PUBLICADO
    // -------------------------------------------------------

    /**
     * GET público por id: retorna o tema apenas se estiver PUBLICADO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TemaResponseDTO> buscarPorId(@PathVariable Long id) {
        Tema tema = temaService.buscarPublicoPorId(id);
        return ResponseEntity.ok(TemaMapper.toResponseDTO(tema));
    }

    /**
     * GET público por plano: retorna apenas temas PUBLICADOS do plano.
     */
    @GetMapping("/plano/{planoId}")
    public ResponseEntity<List<TemaResponseDTO>> listarPorPlano(@PathVariable Long planoId) {
        List<TemaResponseDTO> temas = temaService.listarPublicosPorPlano(planoId)
                .stream()
                .map(TemaMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(temas);
    }

    // -------------------------------------------------------
    // ROTAS AUTENTICADAS — exigem token JWT válido + ownership
    // -------------------------------------------------------

    /**
     * GET interno: retorna o tema independente do status (para o painel do candidato).
     * Requer autenticação e ownership.
     */
    @GetMapping("/{id}/meu")
    public ResponseEntity<TemaResponseDTO> buscarPorIdAutenticado(
            @PathVariable Long id,
            Authentication authentication) {

        Tema tema = temaService.buscarPorId(id);
        validarOwnership(tema.getPlanoDeGoverno(), authentication);
        return ResponseEntity.ok(TemaMapper.toResponseDTO(tema));
    }

    /**
     * GET interno por plano: retorna todos os temas (qualquer status) de um plano.
     * Requer autenticação e ownership.
     */
    @GetMapping("/plano/{planoId}/todos")
    public ResponseEntity<List<TemaResponseDTO>> listarTodosPorPlano(
            @PathVariable Long planoId,
            Authentication authentication) {

        PlanoDeGoverno plano = planoDeGovernoService.buscarPorId(planoId);
        validarOwnership(plano, authentication);

        List<TemaResponseDTO> temas = temaService.listarPorPlano(planoId)
                .stream()
                .map(TemaMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(temas);
    }

    /**
     * POST: cria um tema dentro de um plano.
     * Requer autenticação e ownership sobre o plano.
     */
    @PostMapping("/plano/{planoId}")
    public ResponseEntity<TemaResponseDTO> criar(
            @PathVariable Long planoId,
            @RequestBody @Valid TemaRequestDTO requestDTO,
            Authentication authentication) {

        PlanoDeGoverno plano = planoDeGovernoService.buscarPorId(planoId);
        validarOwnership(plano, authentication);

        Tema tema = TemaMapper.toEntity(requestDTO);
        Tema temaCriado = temaService.criar(planoId, tema);
        return ResponseEntity.status(HttpStatus.CREATED).body(TemaMapper.toResponseDTO(temaCriado));
    }

    /**
     * PUT: atualiza dados de um tema.
     * Requer autenticação e ownership sobre o plano dono do tema.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TemaResponseDTO> atualizarDados(
            @PathVariable Long id,
            @RequestBody @Valid TemaRequestDTO requestDTO,
            Authentication authentication) {

        Tema temaExistente = temaService.buscarPorId(id);
        validarOwnership(temaExistente.getPlanoDeGoverno(), authentication);

        Tema temaAtualizado = TemaMapper.toEntity(requestDTO);
        Tema tema = temaService.atualizarDados(id, temaAtualizado);
        return ResponseEntity.ok(TemaMapper.toResponseDTO(tema));
    }

    /**
     * PATCH status: altera o status de publicação de um tema.
     * Requer autenticação e ownership sobre o plano dono do tema.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TemaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPublicacao status,
            Authentication authentication) {

        Tema temaExistente = temaService.buscarPorId(id);
        validarOwnership(temaExistente.getPlanoDeGoverno(), authentication);

        Tema tema = temaService.atualizarStatus(id, status);
        return ResponseEntity.ok(TemaMapper.toResponseDTO(tema));
    }

    // -------------------------------------------------------
    // HELPER DE OWNERSHIP
    // -------------------------------------------------------

    /**
     * Valida que o candidato autenticado é o dono do plano de governo informado.
     * Lança AccessDeniedException (HTTP 403) se não for.
     */
    private void validarOwnership(PlanoDeGoverno plano, Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        if (!plano.getCandidato().getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para operar sobre temas de outro candidato.");
        }
    }
}
