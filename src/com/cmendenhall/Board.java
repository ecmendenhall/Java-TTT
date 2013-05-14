package com.cmendenhall;

/**
 * Created with IntelliJ IDEA.
 * User: ecm
 * Date: 5/14/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Board {
    boolean hasWin();

    boolean isFull();

    int winnerIs();

    int nextTurn();

    @Override
    String toString();

    boolean equals(Board otherBoard);
}
