package fr.gopartner.tregusto.administration.infrastructure.external;

import fr.gopartner.tregusto.administration.infrastructure.config.RecaptchaProperties;
import fr.gopartner.tregusto.administration.spi.CaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final RestClient restClient;
    private final RecaptchaProperties recaptchaProperties;

    @Override
    public boolean verifyCaptcha(String captcha) {
        try {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("secret", recaptchaProperties.getSecretKey());
            formData.add("response", captcha);

            String response = restClient.post()
                    .uri(recaptchaProperties.getVerificationUrl())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(String.class);

            if (response == null || response.isBlank()) {
                log.warn("Empty response from reCAPTCHA verification");
                return false;
            }

            return response.contains("\"success\": true");
        } catch (Exception e) {
            log.error("reCAPTCHA verification failed: {}", e.getMessage(), e);
            return false;
        }
    }
}
