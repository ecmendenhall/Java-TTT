package com.cmendenhall.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class StringLoader {
    private HashMap<String, String> viewStrings = new HashMap<String, String>();
    private Properties viewStringProperties = new Properties();

    private void load(String propertyName) {
        String propertyString = viewStringProperties.getProperty(propertyName);
        viewStrings.put(propertyName, propertyString);
    }

    private void loadViewStrings(String filepath) {
        try {
            viewStringProperties.load(this.getClass().getResourceAsStream(filepath));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        String[] properties = { "welcome",
                                "divider",
                                "yourmove",
                                "yourmovethreesquares",
                                "playagain",
                                "gameoverdraw",
                                "gameoverwin",
                                "xwins",
                                "owins",
                                "chooseplayerone",
                                "chooseplayertwo",
                                "boardsize"};

        for (String property : properties) {
            load(property);
        }

    }

    public HashMap<String, String> getViewStrings(String filepath) {
        loadViewStrings(filepath);
        return viewStrings;
    }
}