package com.yapily.project.marvel.api;

import com.yapily.project.marvel.model.Response;

import java.util.List;

public interface IController {
    List<Integer> getCharacters();

    Response getCharacter(final int characterId);
}
