package com.yapily.project.marvel.service;

import com.yapily.project.marvel.api.IController;
import com.yapily.project.marvel.exception.DataNotFoundApiException;
import com.yapily.project.marvel.model.Character;
import com.yapily.project.marvel.model.Response;
import com.yapily.project.marvel.util.CharacterJsonParser;
import com.yapily.project.marvel.util.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService implements IController {
    @Value("${MARVEL_CHARACTERS_URI}")
    private String marvelCharactersUri;

    @Autowired
    private MarvelWebService marvelWebService;

    @Autowired
    private CharacterJsonParser characterJsonParser;

    @Autowired
    private FileReader fileReader;

    /**
     * @return list of Marvel characters' ids
     */
    @Override
    public List<Integer> getCharacters() {
        return fileReader.getIds();
    }

    /**
     * user passes character's id
     * Real time call to Marvel API to fetch the character
     *
     * @param characterId
     * @return
     */
    @Override
    public Response getCharacter(final int characterId) {
        final String url = marvelCharactersUri + "/" + characterId;
        Response response = marvelWebService.exchange(url, null, null);
        if (response.getHttpStatus().is2xxSuccessful()) {
            Character character = characterJsonParser.getActualSingleCharacter(response.getMessage());
            return new Response(character, response.getHttpStatus(), HttpStatus.OK.series().toString());
        }
        return new Response(response.getHttpStatus(), response.getMessage());
    }
}
