package com.cmendenhall;

public class Diagonal extends Row {
    final private int _ = 0;

    public Diagonal() {
        squares = new int[] { _, _, _ };
    }

    public Diagonal(int left, int middle, int right) {
        squares = new int[] { left, middle, right };
    }
}
