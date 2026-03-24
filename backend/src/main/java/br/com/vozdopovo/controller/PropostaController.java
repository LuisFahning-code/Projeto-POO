package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.vozdopovo.dto.proposta.PropostaRequestDTO;
import br.com.vozdopovo.dto.proposta.PropostaResponseDTO;
import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.mapper.PropostaMapper;
import br.com.vozdopovo.service.PropostaService;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaService propostaService;

    public PropostaController(PropostaService propostaService) {
        this.propostaService = propostaService;
    }

    @PostMapping("/tema/{temaId}")
    public ResponseEntity<PropostaResponseDTO> criar(@PathVariable Long temaId,
                                                     @RequestBody PropostaRequestDTO requestDTO) {
        Proposta proposta = PropostaMapper.toEntity(requestDTO);
        Proposta propostaCriada = propostaService.criar(temaId, proposta);
        return ResponseEntity.status(HttpStatus.CREATED).body(PropostaMapper.toResponseDTO(propostaCriada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponseDTO> buscarPorId(@PathVariable Long id) {
        Proposta proposta = propostaService.buscarPorId(id);
        return ResponseEntity.ok(PropostaMapper.toResponseDTO(proposta));
    }

    @GetMapping("/tema/{temaId}")
    public ResponseEntity<List<PropostaResponseDTO>> listarPorTema(@PathVariable Long temaId) {
        List<PropostaResponseDTO> propostas = propostaService.listarPorTema(temaId)
                .stream()
                .map(PropostaMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(propostas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropostaResponseDTO> atualizarDados(@PathVariable Long id,
                                                              @RequestBody PropostaRequestDTO requestDTO) {
        Proposta propostaAtualizada = PropostaMapper.toEntity(requestDTO);
        Proposta proposta = propostaService.atualizarDados(id, propostaAtualizada);
        return ResponseEntity.ok(PropostaMapper.toResponseDTO(proposta));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PropostaResponseDTO> atualizarStatus(@PathVariable Long id,
                                                               @RequestParam StatusPublicacao status) {
        Proposta proposta = propostaService.atualizarStatus(id, status);
        return ResponseEntity.ok(PropostaMapper.toResponseDTO(proposta));
    }
}