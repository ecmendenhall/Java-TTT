package com.cmendenhall.board;

import com.cmendenhall.exceptions.InvalidCoordinateException;

public class UniversalBoardCoordinate implements BoardCoordinate {
    private final Integer row;
    private final Integer column;

    public UniversalBoardCoordinate(Integer r, Integer c) {
        row = r;
        column = c;
    }

    private Integer[] parseString(String locationPhrase) throws InvalidCoordinateException {
        String noParens = locationPhrase.replace('(', ' ').replace(')', ' ');
        String[] coordinates = noParens.split(",");
        checkValidity(coordinates);
        return parseCoordinates(coordinates);
    }

    private void checkValidity(String[] coordinates) throws InvalidCoordinateException {
        if (coordinates.length != 2) {
            throw new InvalidCoordinateException("That's not a valid board location.");
        }
    }

    private Integer[] parseCoordinates(String[] coordinates) {
        return new Integer[] { Integer.parseInt(coordinates[0].trim()),
                               Integer.parseInt(coordinates[1].trim()) };
    }

    public UniversalBoardCoordinate(String locationPhrase) throws InvalidCoordinateException {

        Integer[] orderedPair = parseString(locationPhrase);

        row = orderedPair[0];
        column = orderedPair[1];
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

}
