package br.com.vozdopovo.controller;

import br.com.vozdopovo.dto.ia.PerguntaRequestDTO;
import br.com.vozdopovo.dto.ia.PerguntaResponseDTO;
import br.com.vozdopovo.service.IaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ia")
public class IaController {

    private final IaService iaService;

    public IaController(IaService iaService) {
        this.iaService = iaService;
    }

    /**
     * Recebe a pergunta do usuário e retorna a resposta da IA.
     *
     * POST /ia/perguntar
     * Body: { "candidatoId": 1, "pergunta": "Quais são as propostas para saúde?" }
     */
    @PostMapping("/perguntar")
    public ResponseEntity<PerguntaResponseDTO> perguntar(@RequestBody @Valid PerguntaRequestDTO request) {
        PerguntaResponseDTO response = iaService.processarPergunta(request);
        return ResponseEntity.ok(response);
    }
}
