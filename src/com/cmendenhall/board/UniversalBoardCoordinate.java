package com.cmendenhall.board;

import com.cmendenhall.exceptions.InvalidCoordinateException;

public class UniversalBoardCoordinate implements BoardCoordinate {
    private final Integer row;
    private final Integer column;

    public UniversalBoardCoordinate(Integer r, Integer c) {
        row = r;
        column = c;
    }

    private String[] parseString(String locationPhrase) {
        String noParens = locationPhrase.replace('(', ' ').replace(')', ' ');
        return noParens.split(",");
    }

    public UniversalBoardCoordinate(String locationPhrase) throws InvalidCoordinateException {

        String noParens = locationPhrase.replace('(', ' ').replace(')', ' ');
        String[] coordinates = noParens.split(",");

        if (coordinates.length != 2) {
            throw new InvalidCoordinateException("That's not a valid board location.");
        }

        row = Integer.parseInt(coordinates[0].trim());
        column = Integer.parseInt(coordinates[1].trim());
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

}
