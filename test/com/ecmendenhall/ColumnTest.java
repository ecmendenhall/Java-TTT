package com.ecmendenhall;

import org.junit.Before;

public class ColumnTest extends RowTest {

    @Before
    public void setUp() {
        empty = new Column();
        xxo = new Column(X, X, O);
        ooo = new Column(O, O, O);
    }

}
