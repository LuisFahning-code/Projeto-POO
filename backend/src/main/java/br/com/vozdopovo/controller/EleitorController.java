package br.com.vozdopovo.controller;

import br.com.vozdopovo.dto.eleitor.EleitorRequestDTO;
import br.com.vozdopovo.dto.eleitor.EleitorResponseDTO;
import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.mapper.EleitorMapper;
import br.com.vozdopovo.service.EleitorService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eleitores")
public class EleitorController {

    private final EleitorService eleitorService;

    public EleitorController(EleitorService eleitorService) {
        this.eleitorService = eleitorService;
    }

    @PostMapping
    public ResponseEntity<EleitorResponseDTO> criar(@RequestBody @Valid EleitorRequestDTO dto) {
        Eleitor eleitor = EleitorMapper.toEntity(dto);
        Eleitor novoEleitor = eleitorService.criar(eleitor);
        return ResponseEntity.status(HttpStatus.CREATED).body(EleitorMapper.toResponseDTO(novoEleitor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EleitorResponseDTO> buscarPorId(@PathVariable Long id) {
        Eleitor eleitor = eleitorService.buscarPorId(id);
        return ResponseEntity.ok(EleitorMapper.toResponseDTO(eleitor));
    }

    @GetMapping
    public ResponseEntity<List<EleitorResponseDTO>> listarTodosAtivos() {
        List<EleitorResponseDTO> eleitores = eleitorService.listarTodosAtivos()
                .stream()
                .map(EleitorMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(eleitores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EleitorResponseDTO> atualizarDados(@PathVariable Long id,
                                                        @RequestBody @Valid EleitorRequestDTO dto) {
        Eleitor eleitorAtualizado = EleitorMapper.toEntity(dto);
        Eleitor eleitor = eleitorService.atualizarDados(id, eleitorAtualizado);
        return ResponseEntity.ok(EleitorMapper.toResponseDTO(eleitor));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        eleitorService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
