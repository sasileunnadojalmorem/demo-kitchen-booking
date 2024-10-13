package com.oshi.ohsi_back.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override 
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedOriginPatterns("http://192.168.*.*", "http://localhost:*" , "http://localhost:3000")  // 내부 IP 패턴 및 로컬 호스트 허용
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}