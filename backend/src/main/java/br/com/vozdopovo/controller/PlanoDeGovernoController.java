package br.com.vozdopovo.controller;

import br.com.vozdopovo.dto.planoDeGoverno.PlanoDeGovernoRequestDTO;
import br.com.vozdopovo.dto.planoDeGoverno.PlanoDeGovernoResponseDTO;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.mapper.PlanoDeGovernoMapper;
import br.com.vozdopovo.service.PlanoDeGovernoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planos-de-governo")
public class PlanoDeGovernoController {

    private final PlanoDeGovernoService planoDeGovernoService;

    public PlanoDeGovernoController(PlanoDeGovernoService planoDeGovernoService) {
        this.planoDeGovernoService = planoDeGovernoService;
    }

    @PostMapping("/candidato/{candidatoId}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> criar(
            @PathVariable Long candidatoId,
            @RequestBody PlanoDeGovernoRequestDTO requestDTO) {

        PlanoDeGoverno plano = PlanoDeGovernoMapper.toEntity(requestDTO);
        PlanoDeGoverno planoCriado = planoDeGovernoService.criar(candidatoId, plano);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlanoDeGovernoMapper.toResponseDTO(planoCriado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> buscarPorId(@PathVariable Long id) {
        PlanoDeGoverno plano = planoDeGovernoService.buscarPorId(id);
        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    @GetMapping("/candidato/{candidatoId}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> buscarPorCandidatoId(@PathVariable Long candidatoId) {
        PlanoDeGoverno plano = planoDeGovernoService.buscarPorCandidatoId(candidatoId);
        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanoDeGovernoResponseDTO> atualizarDados(
            @PathVariable Long id,
            @RequestBody PlanoDeGovernoRequestDTO requestDTO) {

        PlanoDeGoverno planoAtualizado = PlanoDeGovernoMapper.toEntity(requestDTO);
        PlanoDeGoverno plano = planoDeGovernoService.atualizarDados(id, planoAtualizado);

        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PlanoDeGovernoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPublicacao status) {

        PlanoDeGoverno plano = planoDeGovernoService.atualizarStatus(id, status);
        return ResponseEntity.ok(PlanoDeGovernoMapper.toResponseDTO(plano));
    }
}
