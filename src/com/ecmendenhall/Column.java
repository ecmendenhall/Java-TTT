package com.ecmendenhall;

public class Column extends Row {

    final private int x = 1;
    final private int o = 2;
    final private int _ = 0;

    public Column() {
        squares = new int[] { _, _, _ };
    }

    public Column(int left, int middle, int right) {
        squares = new int[] { left, middle, right };
    }
}
