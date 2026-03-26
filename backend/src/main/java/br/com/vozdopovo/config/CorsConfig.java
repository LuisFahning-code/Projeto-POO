package br.com.vozdopovo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // CORREÇÃO #4: origem do frontend via propriedade configurável
    // Defina em application.properties: cors.allowed-origins=http://localhost:3000
    @Value("${cors.allowed-origins:http://localhost:3000}")
    private String allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // CORREÇÃO #4: origens específicas em vez de "*"
                        // "*" é incompatível com allowCredentials(true)
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        // CORREÇÃO #4: permite envio de credenciais (Authorization header)
                        .allowCredentials(true);
            }
        };
    }
}