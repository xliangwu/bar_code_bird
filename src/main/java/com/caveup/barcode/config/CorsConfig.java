package com.caveup.barcode.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Vincent
 * @date 2018/11/6 9:14
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors_allowed_origins}")
    private String[] origins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = ArrayUtils.isEmpty(origins) ? new String[]{"*"} : origins;

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "HEAD")
                .maxAge(3600);
    }

}