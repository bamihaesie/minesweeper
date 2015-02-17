package model;

import static model.Square.SquareState.COVERED;
import static model.Square.SquareState.UNCOVERED;

public class Square {

    private SquareState state;
    private boolean mine;
    private int nearbyMines;

    public Square(boolean mine) {
        this.mine = mine;
        this.state = COVERED;
        this.nearbyMines = 0;
    }

    public boolean hasMine() {
        return mine;
    }

    public void setAsMine() {
        this.mine = true;
    }

    public void incrementNearbyMines() {
        nearbyMines++;
    }

    public int getNearbyMines() {
        return nearbyMines;
    }

    public boolean isCovered() {
        return state == COVERED;
    }

    public void uncover() {
        this.state = UNCOVERED;
    }

    enum SquareState {
        COVERED,
        UNCOVERED,
    }
}
