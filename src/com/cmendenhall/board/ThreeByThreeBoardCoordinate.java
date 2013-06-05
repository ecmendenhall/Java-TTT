package com.cmendenhall.board;

import com.cmendenhall.exceptions.InvalidCoordinateException;

import java.util.HashMap;
import java.util.HashSet;

public class ThreeByThreeBoardCoordinate implements BoardCoordinate {
    private Integer row;
    private Integer column;

    private final String coordRegEx = "\\s*\\(??\\s*[0-9]+\\s*\\,\\s*[0-9]+\\s*\\)??\\s*";

    private HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
    private HashSet<String> rowWords = new HashSet<String>();
    private HashSet<String> columnWords = new HashSet<String>();

    private void populateWordMap() {
        wordMap.put("top", 0);
        wordMap.put("upper", 0);
        wordMap.put("middle", 1);
        wordMap.put("bottom", 2);
        wordMap.put("lower", 2);
        wordMap.put("left", 0);
        wordMap.put("center", 1);
        wordMap.put("right", 2);

        rowWords.add("top");
        rowWords.add("upper");
        rowWords.add("middle");
        rowWords.add("center");
        rowWords.add("bottom");
        rowWords.add("lower");

        columnWords.add("left");
        columnWords.add("right");
        columnWords.add("center");
        columnWords.add("middle");
    }

    private void makeNaturalPhraseCoordinate(String locationPhrase) throws InvalidCoordinateException {

        String[] words = locationPhrase.split(" ");
        if (words.length != 2) {
            throw new InvalidCoordinateException("That's not a valid board location.");
        }

        String firstWord = words[0].toLowerCase();
        String secondWord = words[1].toLowerCase();

        if (isRowWord(firstWord) && isColumnWord(secondWord)) {
            row = wordMap.get(firstWord);
            column = wordMap.get(secondWord);
        } else if (isColumnWord(firstWord) && isRowWord(secondWord)) {
            column = wordMap.get(firstWord);
            row = wordMap.get(secondWord);
        } else
            throw new InvalidCoordinateException("That's not a valid board location.");

    }

    private void makeOrderedPairCoordinate(String locationPhrase) throws InvalidCoordinateException {

        String noParens = locationPhrase.replace('(', ' ').replace(')', ' ');
        String[] coordinates = noParens.split(",");

        if (coordinates.length != 2) {
            throw new InvalidCoordinateException("That's not a valid board location.");
        }

        row = Integer.parseInt(coordinates[0].trim());
        column = Integer.parseInt(coordinates[1].trim());
    }

    public ThreeByThreeBoardCoordinate(String locationPhrase) throws InvalidCoordinateException {
        if (locationPhrase.matches(coordRegEx)) {
            makeOrderedPairCoordinate(locationPhrase);
        } else {
            populateWordMap();
            makeNaturalPhraseCoordinate(locationPhrase);
        }


    }


    private Boolean isRowWord(String word) {
        return rowWords.contains(word);
    }

    private Boolean isColumnWord(String word) {
        return columnWords.contains(word);
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }
}