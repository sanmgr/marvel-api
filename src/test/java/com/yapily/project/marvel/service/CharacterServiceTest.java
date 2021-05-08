package com.yapily.project.marvel.service;

import com.yapily.project.marvel.model.Character;
import com.yapily.project.marvel.model.Response;
import com.yapily.project.marvel.model.Thumbnail;
import com.yapily.project.marvel.util.CharacterJsonParser;
import com.yapily.project.marvel.util.FileReader;
import com.yapily.project.marvel.util.config.TestApplicationConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplicationConfig.class)
public class CharacterServiceTest {
    @InjectMocks
    CharacterService characterService;

    private MockMvc mockMvc;

    @Mock
    FileReader fileReader;

    @Mock
    MarvelWebService webService;

    @Mock
    CharacterJsonParser characterJsonParser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(characterService).build();
    }

    @Test
    public void testGetCharacters() {
        List<Integer> records = new ArrayList<>();
        records.add(1);
        records.add(2);
        records.add(3);
        when(fileReader.getIds()).thenReturn(records);
        List<Integer> characters = characterService.getCharacters();
        int actualCharacters = characters.size();
        Assert.assertEquals(records.size(), actualCharacters);
    }

    @Test
    public void testGetCharacter() {
        when(webService.exchange(anyString(), anyObject(), anyObject())).thenReturn(getResponse());
        when(characterJsonParser.getActualSingleCharacter(anyString())).thenReturn(getCharacter());
        Response actualResponse = characterService.getCharacter(1);
        Assert.assertNotNull(actualResponse);
        Assert.assertEquals(HttpStatus.OK, actualResponse.getHttpStatus());
        Assert.assertEquals(getCharacter().getId(), actualResponse.getCharacter().getId());
        Assert.assertEquals("SUCCESSFUL", actualResponse.getMessage());
    }

    @Test
    public void testGetCharacterNotOk() {

        when(webService.exchange(anyString(), anyObject(), anyObject())).thenReturn(getResponseNotOk());
        when(characterJsonParser.getActualSingleCharacter(anyString())).thenReturn(new Character());
        Response actualResponse = characterService.getCharacter(1);
        Assert.assertNotNull(actualResponse);
        Assert.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getHttpStatus());
        Assert.assertEquals("NOT FOUND", actualResponse.getMessage());
    }


    public Response getResponseNotOk() {
        Response response = new Response(getCharacter(), HttpStatus.NOT_FOUND, "NOT FOUND");
        return response;
    }

    public Response getResponse() {
        Response response = new Response(getCharacter(), HttpStatus.OK, "SUCCESSFUL");
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