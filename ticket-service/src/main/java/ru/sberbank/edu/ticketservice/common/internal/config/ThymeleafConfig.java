package ru.sberbank.edu.ticketservice.common.internal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Конфигурация Thymeleaf, в частности для поиска ресурсов в кастомной папке
 */
@Configuration
public class ThymeleafConfig {
    @Bean
    public ClassLoaderTemplateResolver secondaryTemplateResolver() {
        var secondaryTemplateResolver = new ClassLoaderTemplateResolver();
        secondaryTemplateResolver.setPrefix("view/");
        secondaryTemplateResolver.setSuffix(".html");
        secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
        secondaryTemplateResolver.setCharacterEncoding("UTF-8");
        return secondaryTemplateResolver;
    }
}
