package fr.gopartner.tregusto.common.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/swagger-ui/index.html");

        registry.addStatusController("/favicon.ico", HttpStatus.NOT_FOUND);
        registry.addStatusController("/robots.txt", HttpStatus.NOT_FOUND);
    }
}
