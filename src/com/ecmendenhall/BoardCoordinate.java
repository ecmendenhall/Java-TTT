package com.ecmendenhall;

import java.util.HashMap;

public class BoardCoordinate {
    private final int row;
    private final int column;

    private HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    BoardCoordinate(int r, int c) {
        row = r;
        column = c;
    }

    BoardCoordinate(String locationphrase) {
        wordMap.put("top", 0);
        wordMap.put("upper", 0);
        wordMap.put("middle", 1);
        wordMap.put("bottom", 2);
        wordMap.put("lower", 2);
        wordMap.put("left", 0);
        wordMap.put("center", 1);
        wordMap.put("right", 2);

        String[] words = locationphrase.split(" ");

        row = wordMap.get(words[0].toLowerCase());
        column = wordMap.get(words[1].toLowerCase());
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
