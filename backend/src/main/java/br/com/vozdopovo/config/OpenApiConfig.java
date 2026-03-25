package br.com.vozdopovo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Configuração do Springdoc OpenAPI.
 *
 * Após adicionar a dependência no pom.xml, a documentação fica disponível em:
 *   Swagger UI  → http://localhost:8080/swagger-ui.html
 *   JSON OpenAPI → http://localhost:8080/v3/api-docs
 *
 * Coloque este arquivo em:
 *   src/main/java/br/com/vozdopovo/config/OpenApiConfig.java
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI vozDoPovoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Voz do Povo API")
                        .description("""
                                API REST do backend da plataforma Voz do Povo.
                                
                                **Enums disponíveis:**
                                - `StatusConta`: ATIVA, DESATIVADA
                                - `StatusPublicacao`: RASCUNHO, PUBLICADO, ARQUIVADO
                                - `TipoInteracao`: DUVIDA, DEMANDA
                                - `StatusInteracao`: RECEBIDA, AGUARDANDO_CONFIRMACAO, EM_ANALISE, RESPONDIDA, FINALIZADA
                                
                                **Formato de erro padrão (RFC 9457 ProblemDetail):**
                                ```json
                                {
                                  "type": "https://vozdopovo.com.br/errors/not-found",
                                  "title": "Recurso não encontrado",
                                  "status": 404,
                                  "detail": "Candidato com id 99 não encontrado.",
                                  "timestamp": "2025-03-25T12:00:00Z",
                                  "path": "/candidatos/99"
                                }
                                ```
                                
                                > **Atenção:** O endpoint `POST /ia/perguntar` depende de um
                                > serviço Python externo rodando em `localhost:8000`. Em
                                > ambiente de desenvolvimento, ele pode retornar erro 503
                                > se o serviço não estiver ativo.
                                """)
                        .version("0.0.1-SNAPSHOT")
                        .contact(new Contact()
                                .name("Time Voz do Povo")
                                .email("dev@vozdopovo.com.br")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Ambiente local")
                ));
    }
}
