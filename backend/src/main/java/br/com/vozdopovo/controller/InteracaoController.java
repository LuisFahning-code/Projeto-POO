package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.vozdopovo.dto.interacao.InteracaoRequestDTO;
import br.com.vozdopovo.dto.interacao.InteracaoResponseDTO;
import br.com.vozdopovo.dto.interacao.InteracaoRespostaRequestDTO;
import br.com.vozdopovo.entity.Interacao;
import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.TipoInteracao;
import br.com.vozdopovo.mapper.InteracaoMapper;
import br.com.vozdopovo.service.InteracaoService;

@RestController
@RequestMapping("/interacoes")
public class InteracaoController {

    private final InteracaoService interacaoService;

    public InteracaoController(InteracaoService interacaoService) {
        this.interacaoService = interacaoService;
    }

    @PostMapping("/eleitor/{eleitorId}/candidato/{candidatoId}")
    public ResponseEntity<InteracaoResponseDTO> criar(@PathVariable Long eleitorId,
                                                      @PathVariable Long candidatoId,
                                                      @RequestBody InteracaoRequestDTO dto) {
        Interacao interacao = InteracaoMapper.toEntity(dto);
        Interacao criada = interacaoService.criar(eleitorId, candidatoId, interacao);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(InteracaoMapper.toResponseDTO(criada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteracaoResponseDTO> buscarPorId(@PathVariable Long id) {
        Interacao interacao = interacaoService.buscarPorId(id);
        return ResponseEntity.ok(InteracaoMapper.toResponseDTO(interacao));
    }

    @GetMapping("/candidato/{candidatoId}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorCandidato(@PathVariable Long candidatoId) {
        List<InteracaoResponseDTO> lista = interacaoService.listarPorCandidato(candidatoId)
                .stream()
                .map(InteracaoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/eleitor/{eleitorId}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorEleitor(@PathVariable Long eleitorId) {
        List<InteracaoResponseDTO> lista = interacaoService.listarPorEleitor(eleitorId)
                .stream()
                .map(InteracaoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorStatus(@PathVariable StatusInteracao status) {
        List<InteracaoResponseDTO> lista = interacaoService.listarPorStatus(status)
                .stream()
                .map(InteracaoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<InteracaoResponseDTO>> listarPorTipo(@PathVariable TipoInteracao tipo) {
        List<InteracaoResponseDTO> lista = interacaoService.listarPorTipo(tipo)
                .stream()
                .map(InteracaoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}/responder")
    public ResponseEntity<InteracaoResponseDTO> responder(@PathVariable Long id,
                                                          @RequestBody InteracaoRespostaRequestDTO dto) {
        Interacao interacao = interacaoService.responder(id, dto.getResposta());
        return ResponseEntity.ok(InteracaoMapper.toResponseDTO(interacao));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<InteracaoResponseDTO> atualizarStatus(@PathVariable Long id,
                                                                @RequestParam StatusInteracao status) {
        Interacao interacao = interacaoService.atualizarStatus(id, status);
        return ResponseEntity.ok(InteracaoMapper.toResponseDTO(interacao));
    }
}
