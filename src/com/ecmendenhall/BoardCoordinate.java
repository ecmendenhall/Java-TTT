package com.ecmendenhall;

import java.util.HashMap;

public class BoardCoordinate {
    public int row;
    public int column;

    private HashMap<String, Integer> wordmap = new HashMap<String, Integer>();

    BoardCoordinate(int r, int c) {
        row = r;
        column = c;
    }

    BoardCoordinate(String locationphrase) {
        wordmap.put("top", 0);
        wordmap.put("middle", 1);
        wordmap.put("bottom", 2);
        wordmap.put("left", 0);
        wordmap.put("center", 1);
        wordmap.put("right", 2);

        String[] words = locationphrase.split(" ");

        row = wordmap.get(words[0].toLowerCase());
        column = wordmap.get(words[1].toLowerCase());
    }
}
