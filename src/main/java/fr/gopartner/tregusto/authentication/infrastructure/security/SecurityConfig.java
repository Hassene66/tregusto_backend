package fr.gopartner.tregusto.authentication.infrastructure.security;

import fr.gopartner.tregusto.authentication.infrastructure.config.AuthenticationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    private final AuthenticationConfig config;

    @Value("${spring.security.oauth2.resourceserver.jwt.principal-claim-name:sub}")
    private String principalClaimName;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/**")
                .authorizeHttpRequests(registry -> {
                    registry
                            .requestMatchers("/api/v1/authentication/test").permitAll()
                            .requestMatchers("/api/v1/reservation/**").permitAll()

                            .requestMatchers("/api/v1/administration/contact/requests").permitAll()
                            .requestMatchers("/api/v1/administration/newsletter/**").permitAll()
                            .anyRequest().authenticated();
                });

        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        return http.build();
    }

    @Bean
    @Order(0)
    @Profile("dev")
    public SecurityFilterChain openSwaggerUI(HttpSecurity http) throws Exception {
        http.securityMatcher("/", "/swagger-ui/**", "/webjars/**")
                .authorizeHttpRequests(registry -> {
                    registry.anyRequest().permitAll();
                });
        return http.build();
    }


    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converters = new DelegatingJwtGrantedAuthoritiesConverter(new JwtGrantedAuthoritiesConverter(), new KeycloakJwtRolesConverter());
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converters);
        jwtAuthenticationConverter.setPrincipalClaimName(principalClaimName);
        return jwtAuthenticationConverter;
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(config.getApiAllowedOrigins()));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
