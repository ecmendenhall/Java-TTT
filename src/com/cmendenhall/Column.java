package com.cmendenhall;

public class Column extends Row {
    final private int _ = 0;

    public Column() {
        squares = new int[] { _, _, _ };
    }

    public Column(int left, int middle, int right) {
        squares = new int[] { left, middle, right };
    }
}
