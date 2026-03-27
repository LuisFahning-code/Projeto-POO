package br.com.vozdopovo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.vozdopovo.security.JwtFilter;

/**
 * Configuração central do Spring Security.
 *
 * Rotas públicas (sem token):
 *   POST /auth/login
 *   POST /candidatos        (cadastro)
 *   POST /eleitores         (cadastro)
 *   GET  /candidatos/**     (consulta pública — apenas candidatos ativos)
 *   GET  /planos-de-governo/{id}            (apenas PUBLICADO)
 *   GET  /planos-de-governo/candidato/**    (apenas PUBLICADO)
 *   GET  /temas/{id}                        (apenas PUBLICADO)
 *   GET  /temas/plano/**                    (apenas PUBLICADO)
 *   GET  /propostas/{id}                    (apenas PUBLICADA)
 *   GET  /propostas/tema/**                 (apenas PUBLICADA)
 *   GET  /swagger-ui/**
 *   GET  /v3/api-docs/**
 *   GET  /h2-console/**
 *
 * Rotas autenticadas com role específico:
 *   POST /ia/perguntar      → ELEITOR
 *
 * Rotas que exigem autenticação (ownership verificado no controller):
 *   GET  /planos-de-governo/{id}/meu
 *   GET  /temas/{id}/meu
 *   GET  /temas/plano/{id}/todos
 *   GET  /propostas/{id}/minha
 *   GET  /propostas/tema/{id}/todas
 *   POST /planos-de-governo/**
 *   POST /temas/**
 *   POST /propostas/**
 *   PATCH/PUT nos recursos acima
 *   /interacoes/**
 *   /eleitores/** (PUT, PATCH)
 *   /candidatos/** (PUT, DELETE)
 *
 * Todas as demais rotas exigem token JWT válido.
 */
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Auth
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                // Cadastro público
                .requestMatchers(HttpMethod.POST, "/candidatos").permitAll()
                .requestMatchers(HttpMethod.POST, "/eleitores").permitAll()

                // Consultas públicas de candidatos
                // ATENÇÃO: matchers exatos — não usa ** para evitar capturar rotas internas futuras
                .requestMatchers(HttpMethod.GET, "/candidatos/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/candidatos/buscar").permitAll()
                .requestMatchers(HttpMethod.GET, "/candidatos/ativos").permitAll()

                // Consultas públicas de planos (apenas PUBLICADOS — filtro no service)
                .requestMatchers(HttpMethod.GET, "/planos-de-governo/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/planos-de-governo/candidato/{candidatoId}").permitAll()

                // Consultas públicas de temas (apenas PUBLICADOS — filtro no service)
                // CORREÇÃO: era "/temas/plano/**" — o wildcard capturava "/temas/plano/{id}/todos" (rota interna)
                .requestMatchers(HttpMethod.GET, "/temas/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/temas/plano/{planoId}").permitAll()

                // Consultas públicas de propostas (apenas PUBLICADAS — filtro no service)
                // CORREÇÃO: era "/propostas/tema/**" — o wildcard capturava "/propostas/tema/{id}/todas" (rota interna)
                .requestMatchers(HttpMethod.GET, "/propostas/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/propostas/tema/{temaId}").permitAll()

                // Endpoint de IA liberado apenas para eleitores autenticados
                .requestMatchers(HttpMethod.POST, "/ia/perguntar").hasRole("ELEITOR")

                // Ferramentas de desenvolvimento
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // Tudo mais exige autenticação (ownership verificado nos controllers)
                .anyRequest().authenticated()
            )
            .headers(h -> h.frameOptions(f -> f.sameOrigin()))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
