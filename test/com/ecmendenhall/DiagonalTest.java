package com.ecmendenhall;

import org.junit.Before;

public class DiagonalTest extends RowTest {

    @Before
    public void setUp() {
        empty = new Diagonal();
        xxo = new Diagonal(X, X, O);
        ooo = new Diagonal(O, O, O);
    }

}
