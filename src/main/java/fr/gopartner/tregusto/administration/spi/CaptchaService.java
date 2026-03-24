package fr.gopartner.tregusto.administration.spi;

public interface CaptchaService {
    boolean verifyCaptcha(String captcha);
}
