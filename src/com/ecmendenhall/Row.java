package com.ecmendenhall;

public class Row {

    public int[] squares;

    public Row() {
        this.squares = int[] { 0, 0, 0 };
    }

    public Row(int left, int middle, int right) {
        this.squares = int[] {left, middle, right };
    }
}
