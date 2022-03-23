package com.steepcliff.thinkboom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSfilter {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedOrigin("http://localhost:3000, http://localhost:8080");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);
        source.registerCorsConfiguration("/user/**", config);
        source.registerCorsConfiguration("/h2-console", config);
        source.registerCorsConfiguration("/randomWord/**", config);
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
        // http://localhost:3000,http://localhost:8080
    }
}
