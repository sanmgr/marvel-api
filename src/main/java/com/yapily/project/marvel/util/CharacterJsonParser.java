package com.yapily.project.marvel.util;

import com.yapily.project.marvel.model.Character;
import com.yapily.project.marvel.model.Thumbnail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CharacterJsonParser {
    private static final Logger LOG = LoggerFactory.getLogger(CharacterJsonParser.class);

    private int total;

    private static final String DATA = "data";
    private static final String TOTAL = "total";
    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String THUMBNAIL = "thumbnail";
    private static final String PATH = "path";
    private static final String EXTENSION = "extension";

    public List<Character> getActualListOfCharacters(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject(DATA);

            int total = data.getInt(TOTAL);
            setTotal(total);

            JSONArray result = data.getJSONArray(RESULTS);
            List<Character> characters = new ArrayList<>();
            for (int i = 0; i < result.length(); i++) {
                JSONObject actualCharacter = (JSONObject) result.get(i);
                Character character = new Character();
                final int actualId = actualCharacter.getInt(ID);
                character.setId(actualId);
                final String actualName = actualCharacter.getString(NAME);
                final String actualDescription = actualCharacter.getString(DESCRIPTION);

                JSONObject actualThumbnail = actualCharacter.getJSONObject(THUMBNAIL);
                final String actualPath = actualThumbnail.getString(PATH);
                final String actualExtension = actualThumbnail.getString(EXTENSION);

                character.setName(actualName);
                character.setDescription(actualDescription);

                Thumbnail thumbnail = new Thumbnail();
                thumbnail.setPath(actualPath);
                thumbnail.setExtension(actualExtension);
                character.setThumbnail(thumbnail);
                characters.add(character);
            }
            return characters;
        } catch (JSONException ex) {
            LOG.error("something failed::: " + ex.getClass(), ex);
            return new ArrayList<>();
        }
    }

    public Character getActualSingleCharacter(final String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject(DATA);
            JSONArray result = data.getJSONArray(RESULTS);
            JSONObject actualCharacter = (JSONObject) result.get(0);
            Character character = new Character();
            final int actualId = actualCharacter.getInt(ID);
            character.setId(actualId);
            final String actualName = actualCharacter.getString(NAME);
            final String actualDescription = actualCharacter.getString(DESCRIPTION);

            JSONObject actualThumbnail = actualCharacter.getJSONObject(THUMBNAIL);
            final String actualPath = actualThumbnail.getString(PATH);
            final String actualExtension = actualThumbnail.getString(EXTENSION);

            character.setName(actualName);
            character.setDescription(actualDescription);

            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setPath(actualPath);
            thumbnail.setExtension(actualExtension);
            character.setThumbnail(thumbnail);

            return character;
        } catch (JSONException ex) {
            LOG.error("something failed::: " + ex.getClass(), ex);
            return new Character();
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
