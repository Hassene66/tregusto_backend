package fr.gopartner.tregusto.common.config;


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
    private RestaurantManagers restaurantManagers;
    
    @Data
    public static class Newsletter {
        private String confirmationUrl;
    }
    
    @Data
    public static class RestaurantManagers {
        private List<String> emails = new ArrayList<>();
    }
}

