package fr.gopartner.tregusto.administration.spi;

import org.springframework.stereotype.Service;

@Service
public class CaptchaValidatorService {

    private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    
    public boolean verifyCaptcha(String captchaToken) {
        return true;
    }
}
