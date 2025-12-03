package org.example.shoppingmall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String uploadPath = System.getProperty("user.dir") + "/uploads/img/";

        registry.addResourceHandler("/img/**")
                .addResourceLocations(
                        "file:" + uploadPath,                // 업로드 이미지
                        "classpath:/static/img/"             // 기존 이미지
                );
    }
}
