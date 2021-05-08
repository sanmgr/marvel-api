package com.yapily.project.marvel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapily.project.marvel.api.IController;
import com.yapily.project.marvel.model.Character;
import com.yapily.project.marvel.model.Response;
import com.yapily.project.marvel.model.Thumbnail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CharacterControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IController characterService;

    @InjectMocks
    CharacterController characterController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(characterController).build();
    }

    @Test
    public void testCharacters() throws Exception {
        when(characterService.getCharacters()).thenReturn(characters());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json(getRequestJson()));
    }

    @Test
    public void testCharacter() throws Exception {
        when(characterService.getCharacter(anyInt())).thenReturn(getResponse());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters/" + 123))
                .andExpect(status().isOk())
                .andExpect(content().json(getRequestJsonResponse()));
    }

    @Test
    public void testCharacterNotFound() throws Exception {
        when(characterService.getCharacter(anyInt())).thenReturn(getResponseNotFound());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/characters/" + 987))
                .andExpect(status().isNotFound())
                .andExpect(content().json(getRequestJsonResponseNotFound()));
    }

    private String getRequestJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(stringWriter, characters());
        return stringWriter.toString();
    }

    public List<Integer> characters() {
        List<Integer> characters = new ArrayList<>();
        characters.add(1);
        characters.add(2);
        characters.add(3);
        return characters;
    }

    private String getRequestJsonResponse() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(stringWriter, getResponse());
        return stringWriter.toString();
    }

    private String getRequestJsonResponseNotFound() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(stringWriter, getResponseNotFound());
        return stringWriter.toString();
    }

    public Response getResponse() {
        Response response = new Response(getCharacter(), HttpStatus.OK, "SUCCESSFUL");
        return response;
    }

    public Response getResponseNotFound() {
        Response response = new Response(getCharacter(), HttpStatus.NOT_FOUND, "NOT FOUND");
        return response;
    }

    public Character getCharacter() {
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setExtension("testExtension");
        thumbnail.setPath("testPath");
        Character character = new Character();
        character.setThumbnail(thumbnail);
        character.setId(12);
        character.setDescription("testDescription");
        character.setName("testName");
        return character;
    }
}