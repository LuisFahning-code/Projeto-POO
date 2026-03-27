package br.com.vozdopovo.handler;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.vozdopovo.exception.base.BusinessException;
import br.com.vozdopovo.exception.base.ResourceNotFoundException;
import br.com.vozdopovo.exception.base.ValidationException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // -------------------------------------------------------
    // 404 — Recurso não encontrado
    // -------------------------------------------------------
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex,
                                                HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());

        problem.setType(URI.create("https://vozdopovo.com.br/errors/not-found"));
        problem.setTitle("Recurso não encontrado");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    // -------------------------------------------------------
    // 422 — Violação de regra de negócio
    // -------------------------------------------------------
    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(BusinessException ex,
                                        HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());

        problem.setType(URI.create("https://vozdopovo.com.br/errors/business-rule-violation"));
        problem.setTitle("Violação de regra de negócio");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    // -------------------------------------------------------
    // 400 — Erros de validação interna (CampoObrigatorioException,
    //        FormatoInvalidoException e qualquer subclasse de ValidationException)
    // -------------------------------------------------------
    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleValidationException(ValidationException ex,
                                                   HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());

        problem.setType(URI.create("https://vozdopovo.com.br/errors/validation"));
        problem.setTitle("Erro de validação");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    // -------------------------------------------------------
    // 400 — Erros de validação de campos (@Valid / @Validated)
    // -------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex,
                                          HttpServletRequest request) {
        List<FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new FieldErrorDetail(fe.getField(), fe.getDefaultMessage()))
                .toList();

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Um ou mais campos possuem valores inválidos.");

        problem.setType(URI.create("https://vozdopovo.com.br/errors/validation"));
        problem.setTitle("Erro de validação");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());
        problem.setProperty("errors", fieldErrors);

        return problem;
    }

    // -------------------------------------------------------
    // 401 — Não autenticado
    // -------------------------------------------------------
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthentication(AuthenticationException ex,
                                              HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, "Autenticação necessária.");

        problem.setType(URI.create("https://vozdopovo.com.br/errors/unauthorized"));
        problem.setTitle("Não autorizado");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    // -------------------------------------------------------
    // 403 — Acesso negado
    // -------------------------------------------------------
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex,
                                            HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este recurso.");

        problem.setType(URI.create("https://vozdopovo.com.br/errors/forbidden"));
        problem.setTitle("Acesso negado");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    // -------------------------------------------------------
    // 500 — Erros inesperados (fallback)
    // -------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex,
                                       HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro inesperado. Tente novamente mais tarde.");

        problem.setType(URI.create("https://vozdopovo.com.br/errors/internal-error"));
        problem.setTitle("Erro interno do servidor");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("path", request.getRequestURI());

        return problem;
    }

    // -------------------------------------------------------
    // Classe auxiliar para detalhar erros de campo
    // -------------------------------------------------------
    public record FieldErrorDetail(String field, String message) {}
}
