package br.com.vozdopovo.controller;

import br.com.vozdopovo.dto.ia.PerguntaRequestDTO;
import br.com.vozdopovo.dto.ia.PerguntaResponseDTO;
import br.com.vozdopovo.service.IaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ia")
public class IaController {

    private final IaService iaService;

    public IaController(IaService iaService) {
        this.iaService = iaService;
    }

    /**
     * Recebe a pergunta do eleitor autenticado e retorna a resposta da IA.
     *
     * MELHORIA #8: o Authentication é injetado pelo Spring Security.
     * O principal do token JWT é o email do usuário autenticado (definido
     * em JwtFilter). Ele é repassado ao service para que o IaServiceImpl
     * possa verificar se o eleitor existe e está ativo antes de processar
     * a pergunta — impedindo que tokens inválidos ou de contas desativadas
     * consumam a API de IA.
     *
     * POST /ia/perguntar
     * Header: Authorization: Bearer <token>
     * Body: { "candidatoId": 1, "pergunta": "Quais são as propostas para saúde?" }
     */
    @PostMapping("/perguntar")
    public ResponseEntity<PerguntaResponseDTO> perguntar(
            @RequestBody @Valid PerguntaRequestDTO request,
            Authentication authentication) {

        // O principal é o email do eleitor autenticado (setado pelo JwtFilter)
        String emailEleitor = (String) authentication.getPrincipal();

        PerguntaResponseDTO response = iaService.processarPergunta(request, emailEleitor);
        return ResponseEntity.ok(response);
    }
}
