package com.proyecto.ticketerra_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {  // Implementar WebMvcConfigurer

    // Configuración de vistas JSP
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // Configuración de archivos estáticos
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Registrar las rutas de archivos estáticos
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
	