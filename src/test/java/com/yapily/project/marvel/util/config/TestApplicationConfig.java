package com.yapily.project.marvel.util.config;

import com.yapily.project.marvel.service.CharacterService;
import com.yapily.project.marvel.service.MarvelWebService;
import com.yapily.project.marvel.util.CharacterJsonParser;
import com.yapily.project.marvel.util.FileReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class TestApplicationConfig {
    @Bean
    public CharacterJsonParser characterJsonParser() {
        return new CharacterJsonParser();
    }

    @Bean
    public FileReader fileReader() {
        return new FileReader();
    }

    @Bean
    public CharacterService characterService() {
        return new CharacterService();
    }

    @Bean
    public MarvelWebService marvelWebService() {
        return new MarvelWebService();
    }
}
