package br.com.vozdopovo.controller;

import br.com.vozdopovo.dto.planoDeGoverno.PlanoDeGovernoRequestDTO;
import br.com.vozdopovo.dto.planoDeGoverno.PlanoDeGovernoResponseDTO;
import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.mapper.PlanoDeGovernoMapper;
import br.com.vozdopovo.service.CandidatoService;
import br.com.vozdopovo.service.PlanoDeGovernoService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planos-de-governo")
public class PlanoDeGovernoController {

    private final PlanoDeGovernoService planoDeGovernoService;
    private final CandidatoService candidatoService;

    public PlanoDeGovernoController(PlanoDeGovernoService planoDeGovernoService,
                                    CandidatoService candidatoService) {
        this.planoDeGovernoService = planoDeGovernoService;
        this.candidatoService = candidatoService;
    }

    // -------------------------------------------------------
    // ROTAS PÚBLICAS — sem autenticação, apenas conteúdo PUBLICADO
    // -------------------------------------------------------

    /**
     * GET público por id: retorna o plano apenas se estiver PUBLICADO.
     * Rota coberta pelo permitAll() no SecurityConfig.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> buscarPorId(@PathVariable Long id) {
        PlanoDeGoverno plano = planoDeGovernoService.buscarPublicoPorId(id);
        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    /**
     * GET público por candidato: retorna o plano do candidato apenas se PUBLICADO.
     */
    @GetMapping("/candidato/{candidatoId}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> buscarPorCandidatoId(
            @PathVariable Long candidatoId) {
        PlanoDeGoverno plano = planoDeGovernoService.buscarPublicoPorCandidatoId(candidatoId);
        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    // -------------------------------------------------------
    // ROTAS AUTENTICADAS — exigem token JWT válido
    // (a restrição efetiva é feita pelo SecurityConfig: anyRequest().authenticated())
    // -------------------------------------------------------

    /**
     * GET interno: retorna o próprio plano independente do status.
     * Usado pelo candidato para ver seu rascunho no painel.
     * Requer autenticação e ownership.
     */
    @GetMapping("/{id}/meu")
    public ResponseEntity<PlanoDeGovernoResponseDTO> buscarPorIdAutenticado(
            @PathVariable Long id,
            Authentication authentication) {

        PlanoDeGoverno plano = planoDeGovernoService.buscarPorId(id);
        validarOwnership(plano, authentication);
        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    /**
     * POST: cria o plano de governo de um candidato.
     * Requer autenticação e que o candidato autenticado seja o dono.
     */
    @PostMapping("/candidato/{candidatoId}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> criar(
            @PathVariable Long candidatoId,
            @RequestBody @Valid PlanoDeGovernoRequestDTO requestDTO,
            Authentication authentication) {

        validarOwnershipPorCandidatoId(candidatoId, authentication);

        PlanoDeGoverno plano = PlanoDeGovernoMapper.toEntity(requestDTO);
        PlanoDeGoverno planoCriado = planoDeGovernoService.criar(candidatoId, plano);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlanoDeGovernoMapper.toResponseDTO(planoCriado));
    }

    /**
     * PATCH dados: atualiza título/apresentação do plano.
     * Requer autenticação e ownership sobre o plano.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> atualizarDados(
            @PathVariable Long id,
            @RequestBody @Valid PlanoDeGovernoRequestDTO requestDTO,
            Authentication authentication) {

        PlanoDeGoverno planoExistente = planoDeGovernoService.buscarPorId(id);
        validarOwnership(planoExistente, authentication);

        PlanoDeGoverno planoAtualizado = PlanoDeGovernoMapper.toEntity(requestDTO);
        PlanoDeGoverno plano = planoDeGovernoService.atualizarDados(id, planoAtualizado);

        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    /**
     * PATCH status: altera o status de publicação do plano.
     * Requer autenticação e ownership sobre o plano.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PlanoDeGovernoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPublicacao status,
            Authentication authentication) {

        PlanoDeGoverno planoExistente = planoDeGovernoService.buscarPorId(id);
        validarOwnership(planoExistente, authentication);

        PlanoDeGoverno plano = planoDeGovernoService.atualizarStatus(id, status);
        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    // -------------------------------------------------------
    // HELPERS DE OWNERSHIP
    // -------------------------------------------------------

    /**
     * Valida que o candidato autenticado é o dono do plano informado.
     * Lança AccessDeniedException (HTTP 403) se não for.
     */
    private void validarOwnership(PlanoDeGoverno plano, Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        if (!plano.getCandidato().getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para operar sobre o plano de outro candidato.");
        }
    }

    /**
     * Valida que o candidato autenticado é o dono identificado pelo candidatoId.
     * Usado no POST de criação, antes de o plano existir.
     */
    private void validarOwnershipPorCandidatoId(Long candidatoId, Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        Candidato candidato = candidatoService.buscarPorId(candidatoId);
        if (!candidato.getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para criar um plano para outro candidato.");
        }
    }
}
