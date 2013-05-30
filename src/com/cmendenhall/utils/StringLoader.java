package com.cmendenhall.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class StringLoader {
    private HashMap<String, String> viewStrings = new HashMap<String, String>();
    private Properties viewStringProperties = new Properties();

    public StringLoader() {
        loadViewStrings();
    }

    private void load(String propertyName) {
        String propertyString = viewStringProperties.getProperty(propertyName);
        viewStrings.put(propertyName, propertyString)
    }

    private void loadViewStrings() {
        try {
            viewStringProperties.load(getClass().getResourceAsStream("/viewstrings.properties"));
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
}