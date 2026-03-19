package fr.gopartner.tregusto.authentication.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("application.authentication")
public class AuthenticationConfig {
    private String[] apiAllowedOrigins;
}

