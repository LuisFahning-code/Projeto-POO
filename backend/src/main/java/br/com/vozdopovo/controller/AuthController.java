package br.com.vozdopovo.controller;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.repository.CandidatoRepository;
import br.com.vozdopovo.repository.EleitorRepository;
import br.com.vozdopovo.security.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CandidatoRepository candidatoRepository;
    private final EleitorRepository   eleitorRepository;
    private final PasswordEncoder      passwordEncoder;
    private final JwtUtil              jwtUtil;

    public AuthController(CandidatoRepository candidatoRepository,
                          EleitorRepository eleitorRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.candidatoRepository = candidatoRepository;
        this.eleitorRepository   = eleitorRepository;
        this.passwordEncoder     = passwordEncoder;
        this.jwtUtil             = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {

        // 1. Tenta encontrar como candidato
        var candidatoOpt = candidatoRepository.findByEmail(request.email());
        if (candidatoOpt.isPresent()) {
            Candidato candidato = candidatoOpt.get();
            if (passwordEncoder.matches(request.senha(), candidato.getSenha())) {

                // CORREÇÃO #1: bloqueia login de contas desativadas
                if (candidato.getStatus() != StatusConta.ATIVA) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(problemDeContaDesativada());
                }

                String token = jwtUtil.gerarToken(candidato.getEmail(), "CANDIDATO");
                return ResponseEntity.ok(new LoginResponse(token, "CANDIDATO",
                        candidato.getId(), candidato.getNome()));
            }
        }

        // 2. Tenta encontrar como eleitor
        var eleitorOpt = eleitorRepository.findByEmail(request.email());
        if (eleitorOpt.isPresent()) {
            Eleitor eleitor = eleitorOpt.get();
            if (passwordEncoder.matches(request.senha(), eleitor.getSenha())) {

                // CORREÇÃO #1: bloqueia login de contas desativadas
                if (eleitor.getStatus() != StatusConta.ATIVA) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(problemDeContaDesativada());
                }

                String token = jwtUtil.gerarToken(eleitor.getEmail(), "ELEITOR");
                return ResponseEntity.ok(new LoginResponse(token, "ELEITOR",
                        eleitor.getId(), eleitor.getNome()));
            }
        }

        // 3. Credenciais inválidas
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos.");
        problem.setType(URI.create("https://vozdopovo.com.br/errors/unauthorized"));
        problem.setTitle("Não autorizado");
        problem.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem);
    }

    private ProblemDetail problemDeContaDesativada() {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, "Esta conta foi desativada e não pode fazer login.");
        problem.setType(URI.create("https://vozdopovo.com.br/errors/unauthorized"));
        problem.setTitle("Conta desativada");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // -------------------------------------------------------
    // DTOs internos do endpoint de login
    // -------------------------------------------------------

    public record LoginRequest(
            @NotBlank(message = "E-mail é obrigatório")
            @Email(message = "Formato de e-mail inválido")
            String email,

            @NotBlank(message = "Senha é obrigatória")
            String senha
    ) {}

    public record LoginResponse(
            String token,
            String role,
            Long   id,
            String nome
    ) {}
}
