package com.yapily.project.marvel.config;

import com.yapily.project.marvel.api.IController;
import com.yapily.project.marvel.service.CharacterService;
import com.yapily.project.marvel.util.CharacterJsonParser;
import com.yapily.project.marvel.util.FileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class ApplicationConfig {

    @Bean
    FileReader fileReader() {
        return new FileReader();
    }

    @Bean
    CharacterJsonParser characterJsonParser() {
        return new CharacterJsonParser();
    }

    @Bean
    public IController controller() {
        return new CharacterService();
    }

}
