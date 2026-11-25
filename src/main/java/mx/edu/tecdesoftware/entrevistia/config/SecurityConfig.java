package mx.edu.tecdesoftware.entrevistia.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * Seguridad (Authorization: Bearer <token de Firebase>):
 * - Leer el catálogo (GET) y Swagger: abiertos, como se vio en clase.
 * - Crear, editar y borrar vacantes: solo usuarios con sesión, para que
 *   nadie en internet pueda modificar el catálogo.
 * - Asistente: con sesión, para proteger la cuota de Gemini.
 * - /resumes/**: con sesión, porque un CV tiene datos personales.
 *
 * El token se valida con las llaves públicas de Google: firma, que no
 * haya expirado, y que sea de NUESTRO proyecto de Firebase.
 */
@Configuration
@SecurityScheme(name = "firebase", type = SecuritySchemeType.HTTP, scheme = "bearer",
        bearerFormat = "JWT", description = "ID token de Firebase del usuario")
public class SecurityConfig {

    private static final String FIREBASE_KEYS =
            "https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // API sin cookies ni formularios
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/vacancies/**", "/categories/**").permitAll()
                        .requestMatchers("/vacancies/**", "/assistant/**", "/resumes/**").authenticated()
                        .anyRequest().permitAll())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(@Value("${firebase.project-id}") String projectId) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(FIREBASE_KEYS).build();
        var issuer = JwtValidators.createDefaultWithIssuer("https://securetoken.google.com/" + projectId);
        var audience = new JwtClaimValidator<List<String>>("aud",
                aud -> aud != null && aud.contains(projectId));
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(issuer, audience));
        return decoder;
    }
}
