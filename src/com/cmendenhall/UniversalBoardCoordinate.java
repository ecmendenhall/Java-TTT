package com.cmendenhall;

import java.util.HashMap;
import java.util.HashSet;

public class UniversalBoardCoordinate implements BoardCoordinate {
    private final Integer row;
    private final Integer column;

    BoardCoordinate(Integer r, Integer c) {
        row = r;
        column = c;
    }

    BoardCoordinate(String locationPhrase) throws InvalidCoordinateException {

        String noParens = locationPhrase.replace('(', ' ').replace(')', ' ');
        String[] coordinates = noParens.split(",");

        if (coordinates.length != 2) {
            throw new InvalidCoordinateException("That's not a valid board location.");
        }

        row = Integer.parseInt(coordinates[0].trim());
        column = Integer.parseInt(coordinates[1].trim());

    }

}
