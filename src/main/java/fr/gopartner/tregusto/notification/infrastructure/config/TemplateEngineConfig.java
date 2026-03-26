package fr.gopartner.tregusto.notification.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class TemplateEngineConfig {

    @Bean
    @Primary
    public TemplateEngine emailTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(emailTemplateResolver());
        return templateEngine;
    }

    private ITemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        // Set the templates folder path relative to classpath
        templateResolver.setPrefix("templates/"); // no leading slash
        templateResolver.setSuffix(".html");

        templateResolver.setTemplateMode("HTML"); // can also use TemplateMode.HTML.name()
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(true);

        // Optional: set order if you have multiple resolvers
        templateResolver.setOrder(1);

        return templateResolver;
    }
}