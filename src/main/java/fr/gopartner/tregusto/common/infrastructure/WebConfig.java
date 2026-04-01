package fr.gopartner.tregusto.common.infrastructure;

import fr.gopartner.tregusto.administration.infrastructure.config.UploadProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UploadProperties uploadProperties;

    public WebConfig(UploadProperties uploadProperties) {
        this.uploadProperties = uploadProperties;
    }

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/swagger-ui/index.html");

        registry.addStatusController("/favicon.ico", HttpStatus.NOT_FOUND);
        registry.addStatusController("/robots.txt", HttpStatus.NOT_FOUND);
        
        registry.addViewController("/preview/contact-request-confirmation")
                .setViewName("contact-request-confirmation");
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadProperties.getBaseDir() + File.separator);
    }
}
