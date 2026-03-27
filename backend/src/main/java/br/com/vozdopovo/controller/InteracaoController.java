package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import br.com.vozdopovo.dto.interacao.InteracaoRequestDTO;
import br.com.vozdopovo.dto.interacao.InteracaoResponseDTO;
import br.com.vozdopovo.dto.interacao.InteracaoRespostaRequestDTO;
import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.entity.Interacao;
import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;
import br.com.vozdopovo.mapper.InteracaoMapper;
import br.com.vozdopovo.service.CandidatoService;
import br.com.vozdopovo.service.EleitorService;
import br.com.vozdopovo.service.InteracaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/interacoes")
public class InteracaoController {

    private final InteracaoService interacaoService;
    private final EleitorService eleitorService;
    private final CandidatoService candidatoService;

    public InteracaoController(InteracaoService interacaoService,
                               EleitorService eleitorService,
                               CandidatoService candidatoService) {
        this.interacaoService = interacaoService;
        this.eleitorService = eleitorService;
        this.candidatoService = candidatoService;
    }

    // -------------------------------------------------------
    // POST — criação de interação
    // -------------------------------------------------------

    /**
     * Cria uma interação de um eleitor para um candidato.
     * Verifica que o eleitor autenticado é o eleitor informado no path.
     */
    @PostMapping("/eleitor/{eleitorId}/candidato/{candidatoId}")
    public ResponseEntity<InteracaoResponseDTO> criar(
            @PathVariable Long eleitorId,
            @PathVariable Long candidatoId,
            @RequestBody @Valid InteracaoRequestDTO dto,
            Authentication authentication) {

        validarOwnershipEleitor(eleitorId, authentication);

        Interacao interacao = InteracaoMapper.toEntity(dto);
        Interacao criada = interacaoService.criar(eleitorId, candidatoId, interacao);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(InteracaoMapper.toResponseDTO(criada));
    }

    // -------------------------------------------------------
    // GET por id
    // -------------------------------------------------------

    /**
     * Retorna uma interação específica.
     * Acessível apenas para o eleitor dono OU o candidato destinatário.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InteracaoResponseDTO> buscarPorId(
            @PathVariable Long id,
            Authentication authentication) {

        Interacao interacao = interacaoService.buscarPorId(id);
        validarAcessoInteracao(interacao, authentication);
        return ResponseEntity.ok(InteracaoMapper.toResponseDTO(interacao));
    }

    // -------------------------------------------------------
    // GET por candidato
    // -------------------------------------------------------

    /**
     * Lista interações de um candidato.
     *
     * Comportamento por role:
     * - CANDIDATO autenticado cujo id corresponde ao candidatoId do path:
     *   vê TODAS as interações daquele candidato.
     * - CANDIDATO autenticado diferente: 403.
     * - ELEITOR autenticado: vê apenas as interações DELE com aquele candidato.
     */
    @GetMapping("/candidato/{candidatoId}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorCandidato(
            @PathVariable Long candidatoId,
            Authentication authentication) {

        String emailAutenticado = (String) authentication.getPrincipal();
        boolean isCandidato = temRole(authentication, "ROLE_CANDIDATO");

        List<InteracaoResponseDTO> lista;

        if (isCandidato) {
            // Candidato: verifica ownership e retorna todas as suas interações
            Candidato candidato = candidatoService.buscarPorId(candidatoId);
            if (!candidato.getEmail().equals(emailAutenticado)) {
                throw new AccessDeniedException(
                        "Você não tem permissão para ver as interações de outro candidato.");
            }
            lista = interacaoService.listarPorCandidato(candidatoId)
                    .stream()
                    .map(InteracaoMapper::toResponseDTO)
                    .toList();
        } else {
            // Eleitor: vê apenas as suas interações com esse candidato
            Eleitor eleitor = eleitorService.buscarPorEmail(emailAutenticado);
            lista = interacaoService.listarPorEleitorECandidato(eleitor.getId(), candidatoId)
                    .stream()
                    .map(InteracaoMapper::toResponseDTO)
                    .toList();
        }

        return ResponseEntity.ok(lista);
    }

    // -------------------------------------------------------
    // GET por eleitor
    // -------------------------------------------------------

    /**
     * Lista interações de um eleitor.
     * O eleitor autenticado só pode ver as suas próprias interações.
     * Candidatos não têm acesso a este endpoint (apenas eleitores).
     */
    @GetMapping("/eleitor/{eleitorId}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorEleitor(
            @PathVariable Long eleitorId,
            Authentication authentication) {

        validarOwnershipEleitor(eleitorId, authentication);

        List<InteracaoResponseDTO> lista = interacaoService.listarPorEleitor(eleitorId)
                .stream()
                .map(InteracaoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    // -------------------------------------------------------
    // GET por status — filtrado pelo usuário autenticado
    // -------------------------------------------------------

    /**
     * Lista interações com um determinado status.
     *
     * Comportamento por role:
     * - CANDIDATO: vê apenas as suas interações com aquele status.
     * - ELEITOR: vê apenas as suas interações com aquele status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorStatus(
            @PathVariable StatusInteracao status,
            Authentication authentication) {

        String emailAutenticado = (String) authentication.getPrincipal();
        boolean isCandidato = temRole(authentication, "ROLE_CANDIDATO");

        List<InteracaoResponseDTO> lista;

        if (isCandidato) {
            Candidato candidato = candidatoService.buscarPorEmail(emailAutenticado);
            lista = interacaoService.listarPorCandidatoEStatus(candidato.getId(), status)
                    .stream()
                    .map(InteracaoMapper::toResponseDTO)
                    .toList();
        } else {
            Eleitor eleitor = eleitorService.buscarPorEmail(emailAutenticado);
            lista = interacaoService.listarPorEleitorEStatus(eleitor.getId(), status)
                    .stream()
                    .map(InteracaoMapper::toResponseDTO)
                    .toList();
        }

        return ResponseEntity.ok(lista);
    }

    // -------------------------------------------------------
    // GET por tipo — filtrado pelo usuário autenticado
    // -------------------------------------------------------

    /**
     * Lista interações de um determinado tipo.
     *
     * Comportamento por role:
     * - CANDIDATO: vê apenas as suas interações com aquele tipo.
     * - ELEITOR: vê apenas as suas interações com aquele tipo.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorTipo(
            @PathVariable TipoInteracao tipo,
            Authentication authentication) {

        String emailAutenticado = (String) authentication.getPrincipal();
        boolean isCandidato = temRole(authentication, "ROLE_CANDIDATO");

        List<InteracaoResponseDTO> lista;

        if (isCandidato) {
            Candidato candidato = candidatoService.buscarPorEmail(emailAutenticado);
            lista = interacaoService.listarPorCandidatoETipo(candidato.getId(), tipo)
                    .stream()
                    .map(InteracaoMapper::toResponseDTO)
                    .toList();
        } else {
            Eleitor eleitor = eleitorService.buscarPorEmail(emailAutenticado);
            lista = interacaoService.listarPorEleitorETipo(eleitor.getId(), tipo)
                    .stream()
                    .map(InteracaoMapper::toResponseDTO)
                    .toList();
        }

        return ResponseEntity.ok(lista);
    }

    // -------------------------------------------------------
    // PATCH — responder e atualizar status
    // -------------------------------------------------------

    /**
     * O candidato destinatário responde a uma interação.
     * Verifica que o candidato autenticado é o destinatário.
     */
    @PatchMapping("/{id}/responder")
    public ResponseEntity<InteracaoResponseDTO> responder(
            @PathVariable Long id,
            @RequestBody @Valid InteracaoRespostaRequestDTO dto,
            Authentication authentication) {

        Interacao interacao = interacaoService.buscarPorId(id);
        validarOwnershipCandidatoInteracao(interacao, authentication);

        Interacao respondida = interacaoService.responder(id, dto.getResposta());
        return ResponseEntity.ok(InteracaoMapper.toResponseDTO(respondida));
    }

    /**
     * O candidato destinatário atualiza o status de uma interação.
     * Verifica que o candidato autenticado é o destinatário.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<InteracaoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusInteracao status,
            Authentication authentication) {

        Interacao interacao = interacaoService.buscarPorId(id);
        validarOwnershipCandidatoInteracao(interacao, authentication);

        Interacao atualizada = interacaoService.atualizarStatus(id, status);
        return ResponseEntity.ok(InteracaoMapper.toResponseDTO(atualizada));
    }

    // -------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------

    /**
     * Verifica que o eleitor autenticado corresponde ao eleitorId do path.
     * Lança AccessDeniedException (HTTP 403) se não corresponder.
     */
    private void validarOwnershipEleitor(Long eleitorId, Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        Eleitor eleitor = eleitorService.buscarPorId(eleitorId);
        if (!eleitor.getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para realizar esta ação em nome de outro eleitor.");
        }
    }

    /**
     * Verifica que o candidato autenticado é o destinatário da interação.
     * Lança AccessDeniedException (HTTP 403) se não for.
     */
    private void validarOwnershipCandidatoInteracao(Interacao interacao,
                                                     Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        if (!interacao.getCandidato().getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException(
                    "Você não tem permissão para responder a uma interação de outro candidato.");
        }
    }

    /**
     * Verifica que o usuário autenticado é o eleitor dono OU o candidato
     * destinatário da interação. Usado no GET por id.
     */
    private void validarAcessoInteracao(Interacao interacao, Authentication authentication) {
        String emailAutenticado = (String) authentication.getPrincipal();
        boolean isEleitorDono = interacao.getEleitor().getEmail().equals(emailAutenticado);
        boolean isCandidatoDestinatario = interacao.getCandidato().getEmail().equals(emailAutenticado);
        if (!isEleitorDono && !isCandidatoDestinatario) {
            throw new AccessDeniedException(
                    "Você não tem permissão para acessar esta interação.");
        }
    }

    /**
     * Verifica se o usuário autenticado possui o role indicado.
     * Usa as authorities já populadas pelo JwtFilter no SecurityContext.
     */
    private boolean temRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }
}
