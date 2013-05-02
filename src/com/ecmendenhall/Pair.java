package com.ecmendenhall;

public class Pair<FIRST, REST> {
    public final FIRST first;
    public final REST rest;

    public Pair(FIRST firstthing, REST more) {
        first = firstthing;
        rest = more;
    }
}
