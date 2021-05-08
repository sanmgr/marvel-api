package com.yapily.project.marvel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileReaderDriver {
    public static void main(String[] args) {

        String path = "C:\\Users\\Sandesh\\Documents\\projects\\api\\marvel\\src\\main\\resources\\characters\\";
        File directory = new File(path);
        List<Integer> ids = new ArrayList<>();
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
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Collections.sort(ids);
        System.out.println(Arrays.asList(ids));
    }
}
