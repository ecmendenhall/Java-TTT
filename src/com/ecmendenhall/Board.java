package com.ecmendenhall;

import java.util.Arrays;

public class Board {
    int[] squares = new int[9];

    public boolean hasWin() {
        return false;
    }

    public int[][] getRows() {
        int[][] rows = new int[3][3];
        for (int i=0; i < rows.length; i++) {
            int leftsquare = i + 3;
            int rightsquare = i + 6;
            rows[i] = Arrays.copyOfRange(this.squares, leftsquare, rightsquare);
        }
        return rows;
    }

    public int[][] getColumns() {
        int[][] columns = new int[3][3];
        return columns;
    }

}
