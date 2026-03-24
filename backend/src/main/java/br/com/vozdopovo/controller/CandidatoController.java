package br.com.vozdopovo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vozdopovo.mapper.CandidatoMapper;
import br.com.vozdopovo.dto.candidato.CandidatoRequestDTO;
import br.com.vozdopovo.dto.candidato.CandidatoResponseDTO;
import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.service.CandidatoService;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

    private final CandidatoService candidatoService;

    public CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    // Retorna a criação de um novo Candidato
    @PostMapping
    public ResponseEntity<CandidatoResponseDTO> criar(@RequestBody CandidatoRequestDTO dto) {
        Candidato candidato = CandidatoMapper.toEntity(dto);
        Candidato novoCandidato = candidatoService.criar(candidato);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CandidatoMapper.toResponseDTO(novoCandidato));
    }

    // Retorna a busca de um Candidato pelo seu Id
    @GetMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> buscarPorId(@PathVariable Long id) {
        Candidato candidato = candidatoService.buscarPorId(id);
        return ResponseEntity.ok(CandidatoMapper.toResponseDTO(candidato));
    }

    // Retorna a listagem dos Candidatos pelo nome buscado
    @GetMapping("/buscar")
    public ResponseEntity<List<CandidatoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        List<Candidato> candidatos = candidatoService.buscarPorNome(nome);

        List<CandidatoResponseDTO> response = candidatos.stream()
                .map(CandidatoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Retorna a listagem de todos os Candidatos com StatusConta ATIVA
    @GetMapping("/ativos")
    public ResponseEntity<List<CandidatoResponseDTO>> listarTodosAtivos() {
        List<Candidato> candidatos = candidatoService.listarTodosAtivos();

        List<CandidatoResponseDTO> response = candidatos.stream()
                .map(CandidatoMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    // Retorna a atualização dos dados do Candidato que foram modificados
    @PutMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> atualizarDados(
            @PathVariable Long id,
            @RequestBody CandidatoRequestDTO dto) {

        Candidato candidatoAtualizado = CandidatoMapper.toEntity(dto);
        Candidato candidato = candidatoService.atualizarDados(id, candidatoAtualizado);

        return ResponseEntity.ok(CandidatoMapper.toResponseDTO(candidato));
    }

    // Retorna a atualização do StatusConta do Candidato para DESATIVADA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        candidatoService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
