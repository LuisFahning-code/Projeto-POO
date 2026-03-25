package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.vozdopovo.dto.tema.TemaRequestDTO;
import br.com.vozdopovo.dto.tema.TemaResponseDTO;
import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.mapper.TemaMapper;
import br.com.vozdopovo.service.TemaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/temas")
public class TemaController {

    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @PostMapping("/plano/{planoId}")
    public ResponseEntity<TemaResponseDTO> criar(@PathVariable Long planoId,
                                                 @RequestBody @Valid TemaRequestDTO requestDTO) {
        Tema tema = TemaMapper.toEntity(requestDTO);
        Tema temaCriado = temaService.criar(planoId, tema);
        return ResponseEntity.status(HttpStatus.CREATED).body(TemaMapper.toResponseDTO(temaCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemaResponseDTO> buscarPorId(@PathVariable Long id) {
        Tema tema = temaService.buscarPorId(id);
        return ResponseEntity.ok(TemaMapper.toResponseDTO(tema));
    }

    @GetMapping("/plano/{planoId}")
    public ResponseEntity<List<TemaResponseDTO>> listarPorPlano(@PathVariable Long planoId) {
        List<TemaResponseDTO> temas = temaService.listarPorPlano(planoId)
                .stream()
                .map(TemaMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(temas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TemaResponseDTO> atualizarDados(@PathVariable Long id,
                                                          @RequestBody @Valid TemaRequestDTO requestDTO) {
        Tema temaAtualizado = TemaMapper.toEntity(requestDTO);
        Tema tema = temaService.atualizarDados(id, temaAtualizado);
        return ResponseEntity.ok(TemaMapper.toResponseDTO(tema));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TemaResponseDTO> atualizarStatus(@PathVariable Long id,
                                                           @RequestParam StatusPublicacao status) {
        Tema tema = temaService.atualizarStatus(id, status);
        return ResponseEntity.ok(TemaMapper.toResponseDTO(tema));
    }
}
