package com.ecmendenhall;

import java.util.HashMap;

public class BoardCoordinate {
    private final int row;
    private final int column;

    private HashMap<String, Integer> wordmap = new HashMap<String, Integer>();

    BoardCoordinate(int r, int c) {
        row = r;
        column = c;
    }

    BoardCoordinate(String locationphrase) {
        wordmap.put("top", 0);
        wordmap.put("upper", 0);
        wordmap.put("middle", 1);
        wordmap.put("bottom", 2);
        wordmap.put("lower", 2);
        wordmap.put("left", 0);
        wordmap.put("center", 1);
        wordmap.put("right", 2);

        String[] words = locationphrase.split(" ");

        row = wordmap.get(words[0].toLowerCase());
        column = wordmap.get(words[1].toLowerCase());
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
