package com.yapily.project.marvel.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reads the files inside the file upload directory one by one
 * Stores the ids found in a arraylist
 * Returns the list of ids
 */
public class FileReader {

    private static final Logger LOG = LoggerFactory.getLogger(FileReader.class);

    @Value("${FILE_UPLOAD_DIRECTORY}")
    private String fileUploadDirectory;

    public List<Integer> getIds() {
        File directory = new File(fileUploadDirectory);
        List<Integer> ids = new ArrayList<>();

        try {
            if (directory.isDirectory()) {
                if (directory.list().length > 0) {
                    File[] files = directory.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            JSONParser parser = new JSONParser();
                            try {
                                Object obj = parser.parse(new java.io.FileReader(file));
                                JSONArray jsonArray = (JSONArray) obj;

                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject character = (JSONObject) jsonArray.get(i);
                                    String actualId = character.get("id").toString();
                                    int id = Integer.parseInt(actualId);
                                    ids.add(id);
                                }
                            } catch (ParseException ex) {
                                LOG.error("ERROR >> Cannot parse the file:: " + ex.getClass(), ex);
                            } catch (FileNotFoundException ex) {
                                LOG.error("ERROR >> File not found:: " + ex.getClass(), ex);
                            } catch (IOException ex) {
                                LOG.error("ERROR >> something failed:: " + ex.getClass(), ex);
                            }
                        }
                    }
                }
            } else {
                LOG.error("It is not a directory");
            }
        } catch (Exception ex) {
            LOG.error("something failed::: " + ex.getClass(), ex);
        }

        if (!ids.isEmpty()) {
            Collections.sort(ids);
        }
        return ids;
    }
}