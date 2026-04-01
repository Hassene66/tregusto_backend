package fr.gopartner.tregusto.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.upload")
public class ImageUploadConfig {
    private String baseDir = "uploads";
    private long maxFileSize = 5 * 1024 * 1024;
    private List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "webp");
}
