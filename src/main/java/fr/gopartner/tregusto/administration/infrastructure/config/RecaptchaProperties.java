package fr.gopartner.tregusto.administration.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.recaptcha")
@Getter
@Setter
public class RecaptchaProperties {
    private String secretKey;
    private String verificationUrl;
}
