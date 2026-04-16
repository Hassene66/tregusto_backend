package fr.gopartner.tregusto.notification.internal;

import fr.gopartner.tregusto.common.config.ApplicationConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final ApplicationConfig applicationConfig;

    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public void sendContactConfirmation(Long requestId, String recipientName, String recipientEmail, String message) throws MessagingException {

        Map<String, Object> variables = Map.of(
                "requestId", requestId,
                "recipientName", recipientName,
                "recipientEmail", recipientEmail,
                "message", message,
                "appName", applicationConfig.getName(),
                "appUrl", applicationConfig.getPublicUrl()
        );

        String subject = String.format("Accusé de réception – Tregusto (Réf. #%s)", requestId);


        sendHtmlEmail(recipientEmail, subject, "contact-request-confirmation", variables);

    }

    @Override
    public void sendNewsletterConfirmation(String email, String token) throws MessagingException {
        String confirmationUrl = applicationConfig.getNewsletter().getConfirmationUrl() + "?token=" + token;

        Map<String, Object> variables = Map.of(
                "confirmationUrl", confirmationUrl,
                "appName", applicationConfig.getName(),
                "appUrl", applicationConfig.getPublicUrl()
        );

        String subject = "Confirmez votre abonnement à la newsletter Tregusto";

        sendHtmlEmail(email, subject, "newsletter-confirmation", variables);
    }


    private void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(templateName, context);

        helper.setFrom(fromAddress);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

}
