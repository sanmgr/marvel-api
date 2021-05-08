package com.yapily.project.marvel.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapily.project.marvel.model.Character;
import com.yapily.project.marvel.model.Response;
import com.yapily.project.marvel.service.MarvelWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
 * CharactersGenerator is used to fetch Marvels's characters API.
 * Fetch only the IDs if possible
 * Convert JSON values to list of Character IDs and store in a file
 * Removes files inside /src/resources/characters folder
 * Adds files inside /src/resources/characters folder
 * To improve, could create many files depending on the size of the IDs e.g. every batch of 100 IDs will be store in a new file
 * Save IDs in ascending order, optimize when fetching
 * You can trigger it manually or make a CRON job
 */
public class CharactersGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(CharactersGenerator.class);

    private static final String ALL_MARVEL_CHARACTERS_URI = "https://gateway.marvel.com:443/v1/public/characters";
    private static final Integer LIMIT = 100;

    public static void main(String[] args) {
        LOG.info("Marvel characters generator triggered");

        MarvelWebService marvelWebService = new MarvelWebService();
        // 1. Make a REST call to Marvel API to fetch all the first 100 characters
        // Offset set to 0, Limit is always 100
        // Plan is to record total number of characters available therefore can set offset for next API call
        Response first100thCharactersResponse = marvelWebService.exchange(ALL_MARVEL_CHARACTERS_URI, 0, LIMIT);

        // 2. After response from Marvel, parse the JSON
        // Change it to our Character object then save list of characters
        CharacterJsonParser jsonParser = new CharacterJsonParser();
        List<Character> first100thCharacters = jsonParser.getActualListOfCharacters(first100thCharactersResponse.getMessage());

        // 3. We have list of first 100th characters
        // Store these list in /src/resources/characters directory
        ObjectMapper mapper = new ObjectMapper();
        String path = "C:\\Users\\Sandesh\\Documents\\projects\\api\\marvel\\src\\main\\resources\\characters\\";
        try {
            File directory = new File(path);
            // If there are any files already in this directory then remove it so that we can have fresh copy
            if (directory.isDirectory()) {
                if (directory.list().length > 0) {
                    deleteDir(directory);
                }
                LOG.info("Saving characters1To100");
                mapper.writeValue(new File(path + "characters1To100.json"), first100thCharacters);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4. Found total number of characters from the first Marvel API call.
        // Plan is to find the offset number (pagination) i.e. how many times we have to call Marvel API to get all the records
        // e.g. Found total is 1493 and limit is 100.
        // Here, 1493/100 is 14.93, rounding up to 14 since first call is already performed
        // Will make 14 more calls now
        // OffSet will be array of index * 100, every 100
        int totalCharacters = jsonParser.getTotal();
        LOG.info("Found total characters :{}", totalCharacters);
        double calculateOffSet = 1 * (Math.floor((double) totalCharacters / LIMIT) / 1);
        int totalOffSet = (int) calculateOffSet;


        // 5. Making other calls to get the all Marvel characters
        // Reading the JSON and parsing as our Character object
        // Storing the JSON in new file separately.
        // Files are stored in a batch rather than in a big dump.
        for (int i = 1; i <= totalOffSet; i++) {
            int offset = i * 100;
            Response charactersResponse = marvelWebService.exchange(ALL_MARVEL_CHARACTERS_URI, offset, LIMIT);
            List<Character> characters = jsonParser.getActualListOfCharacters(charactersResponse.getMessage());
            try {
                int startingPage = offset + 1;
                int endingPage = offset + LIMIT;
                String fileFormat = ".json";
                String fileName = "characters" + startingPage + "To" + endingPage;
                LOG.info("Saving " + fileName);
                mapper.writeValue(new File(path + fileName + fileFormat), characters);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}