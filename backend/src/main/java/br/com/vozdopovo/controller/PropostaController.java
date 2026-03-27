package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import br.com.vozdopovo.dto.proposta.PropostaRequestDTO;
import br.com.vozdopovo.dto.proposta.PropostaResponseDTO;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.mapper.PropostaMapper;
import br.com.vozdopovo.service.PropostaService;
import br.com.vozdopovo.service.TemaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaService propostaService;
    private final TemaService temaService;

    public PropostaController(PropostaService propostaService,
                               TemaService temaService) {
        this.propostaService = propostaService;
        this.temaService = temaService;
    }

    // -------------------------------------------------------
    // ROTAS PÚBLICAS — sem autenticação, apenas conteúdo PUBLICADO
    // -------------------------------------------------------

    /**
     * GET público por id: retorna a proposta apenas se estiver PUBLICADA.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponseDTO> buscarPorId(@PathVariable Long id) {
        Proposta proposta = propostaService.buscarPublicaPorId(id);
        return ResponseEntity.ok(PropostaMapper.toResponseDTO(proposta));
    }

    /**
     * GET público por tema: retorna apenas propostas PUBLICADAS do tema.
     */
    @GetMapping("/tema/{temaId}")
    public ResponseEntity<List<PropostaResponseDTO>> listarPorTema(@PathVariable Long temaId) {
        List<PropostaResponseDTO> propostas = propostaService.listarPublicasPorTema(temaId)
                .stream()
                .map(PropostaMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(propostas);
    }

    // -------------------------------------------------------
    // ROTAS AUTENTICADAS — exigem token JWT válido + ownership
    // -------------------------------------------------------

    /**
     * GET interno: retorna a proposta independente do status (para o painel do candidato).
     * Requer autenticação e ownership.
     */
    @GetMapping("/{id}/minha")
    public ResponseEntity<PropostaResponseDTO> buscarPorIdAutenticado(
            @PathVariable Long id,
            Authentication authentication) {

        Proposta proposta = propostaService.buscarPorId(id);
        validarOwnership(proposta.getPlanoDeGoverno(), authentication);
        return ResponseEntity.ok(PropostaMapper.toResponseDTO(proposta));
    }

    /**
     * GET interno por tema: retorna todas as propostas (qualquer status) de um tema.
     * Requer autenticação e ownership.
     */
    @GetMapping("/tema/{temaId}/todas")
    public ResponseEntity<List<PropostaResponseDTO>> listarTodasPorTema(
            @PathVariable Long temaId,
            Authentication authentication) {

        Tema tema = temaService.buscarPorId(temaId);
        validarOwnership(tema.getPlanoDeGoverno(), authentication);

        List<PropostaResponseDTO> propostas = propostaService.listarPorTema(temaId)
                .stream()
                .map(PropostaMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(propostas);
    }

    /**
     * POST: cria uma proposta dentro de um tema.
     * Requer autenticação e ownership sobre o plano pai do tema.
     */
    @PostMapping("/tema/{temaId}")
    public ResponseEntity<PropostaResponseDTO> criar(
            @PathVariable Long temaId,
            @RequestBody @Valid PropostaRequestDTO requestDTO,
            Authentication authentication) {

        Tema tema = temaService.buscarPorId(temaId);
        validarOwnership(tema.getPlanoDeGoverno(), authentication);

        Proposta proposta = PropostaMapper.toEntity(requestDTO);
        Proposta propostaCriada = propostaService.criar(temaId, proposta);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PropostaMapper.toResponseDTO(propostaCriada));
    }

    /**
     * PUT: atualiza dados de uma proposta.
     * Requer autenticação e ownership sobre o plano pai.
     * Nota: @Valid removido de @PathVariable (era incorreto — problema 4).
     */
    @PutMapping("/{id}")
    public ResponseEntity<PropostaResponseDTO> atualizarDados(
            @PathVariable Long id,
            @RequestBody @Valid PropostaRequestDTO requestDTO,
            Authentication authentication) {

        Proposta propostaExistente = propostaService.buscarPorId(id);
        validarOwnership(propostaExistente.getPlanoDeGoverno(), authentication);

        Proposta propostaAtualizada = PropostaMapper.toEntity(requestDTO);
        Proposta proposta = propostaService.atualizarDados(id, propostaAtualizada);
        return ResponseEntity.ok(PropostaMapper.toResponseDTO(proposta));
    }

    /**
     * PATCH status: altera o status de publicação de uma proposta.
     * Requer autenticação e ownership sobre o plano pai.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PropostaResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPublicacao status,
            Authentication authentication) {

        Proposta propostaExistente = propostaService.buscarPorId(id);
        validarOwnership(propostaExistente.getPlanoDeGoverno(), authentication);

        Proposta proposta = propostaService.atualizarStatus(id, status);
        return ResponseEntity.ok(PropostaMapper.toResponseDTO(proposta));
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
                    "Você não tem permissão para operar sobre propostas de outro candidato.");
        }
    }
}
