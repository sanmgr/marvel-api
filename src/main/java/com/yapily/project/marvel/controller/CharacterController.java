package com.yapily.project.marvel.controller;

import com.yapily.project.marvel.api.IController;
import com.yapily.project.marvel.exception.DataNotFoundApiException;
import com.yapily.project.marvel.exception.ReadTimeOutException;
import com.yapily.project.marvel.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CharacterController {
    @Autowired
    private IController characterService;

    @GetMapping(value = "/characters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> characters() {
        List<Integer> characters = characterService.getCharacters();
        if (characters.isEmpty()) {
            throw new DataNotFoundApiException("Not found");
        } else {
            return new ResponseEntity<>(characters, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/characters/{characterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> character(@PathVariable("characterId") Integer characterId) {
        Response character = characterService.getCharacter(characterId);
        if (character.getHttpStatus() == HttpStatus.OK) {
            return new ResponseEntity<>(character, character.getHttpStatus());
        } else {
            return httpStatusErrorHandler(character);
        }
    }

    private ResponseEntity<Response> httpStatusErrorHandler(Response response) {
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (response.getHttpStatus() == HttpStatus.REQUEST_TIMEOUT) {
            throw new ReadTimeOutException("REQUEST_TIMEOUT");
        } else {
            throw new DataNotFoundApiException("NOT FOUND");
        }
    }
}
