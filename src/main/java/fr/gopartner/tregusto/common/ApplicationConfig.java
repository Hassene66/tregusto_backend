package fr.gopartner.tregusto.common;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix = "application.config")
public class ApplicationConfig {
    private String name;
    private String publicUrl;
    private List<String> allowedOrigins = new ArrayList<>();
    private Newsletter newsletter;
    
    @Data
    public static class Newsletter {
        private String confirmationUrl;
    }
}

