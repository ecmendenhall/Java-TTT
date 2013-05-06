package com.ecmendenhall;

import java.util.HashMap;

public class BoardCoordinate {
    private final Integer row;
    private final Integer column;

    private HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    BoardCoordinate(Integer r, Integer c) {
        row = r;
        column = c;
    }

    BoardCoordinate(String locationphrase) throws InvalidCoordinateException {
        wordMap.put("top", 0);
        wordMap.put("upper", 0);
        wordMap.put("middle", 1);
        wordMap.put("bottom", 2);
        wordMap.put("lower", 2);
        wordMap.put("left", 0);
        wordMap.put("center", 1);
        wordMap.put("right", 2);

        String[] words = locationphrase.split(" ");
        if (words.length == 1) {
            throw new InvalidCoordinateException("That's not a valid board location.");
        }

        row = wordMap.get(words[0].toLowerCase());
        column = wordMap.get(words[1].toLowerCase());

        if (row == null || column == null) {
            throw new InvalidCoordinateException("That's not a valid board location.");
        }
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }
}
