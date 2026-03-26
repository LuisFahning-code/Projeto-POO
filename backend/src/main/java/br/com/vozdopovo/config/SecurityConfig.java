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
 *   GET  /candidatos/**     (consulta pública)
 *   GET  /planos-de-governo/**
 *   GET  /temas/**
 *   GET  /propostas/**
 *   GET  /swagger-ui/**
 *   GET  /v3/api-docs/**
 *   GET  /h2-console/**
 *
 * Rotas autenticadas por role:
 *   POST /ia/perguntar      → ELEITOR
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

                // Consultas públicas
                .requestMatchers(HttpMethod.GET, "/candidatos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/planos-de-governo/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/temas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/propostas/**").permitAll()

                // CORREÇÃO #3: endpoint de IA liberado apenas para eleitores autenticados
                .requestMatchers(HttpMethod.POST, "/ia/perguntar").hasRole("ELEITOR")

                // Ferramentas de desenvolvimento
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // Tudo mais exige autenticação
                .anyRequest().authenticated()
            )
            // Permite o frame do H2 console
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