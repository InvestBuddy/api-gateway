package tech.investbuddy.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable() // Désactiver CSRF pour les API REST
                .cors(Customizer.withDefaults()) // Activer la configuration CORS par défaut
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/users/auth/**").permitAll() // Accès public
                        .anyRequest().authenticated()) // Toutes les autres requêtes nécessitent une authentification
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())) // Configurer OAuth2 Resource Server
                .build();
    }
}
