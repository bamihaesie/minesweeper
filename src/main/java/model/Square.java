package model;

import exception.ExplosionException;

import static model.Square.SquareState.*;

public class Square {

    enum SquareState {
        COVERED,
        COVERED_AND_FLAGGED,
        UNCOVERED,
        EXPLODED
    }

    private SquareState state;
    private boolean mine;
    private int nearbyMines;

    public Square(boolean mine) {
        this.mine = mine;
        state = COVERED;
        nearbyMines = 0;
    }

    public boolean hasMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void uncover() throws ExplosionException {
        this.state = UNCOVERED;
        if (hasMine()) {
            this.state = EXPLODED;
            throw new ExplosionException();
        }
    }

    public void incrementNearbyMines() {
        nearbyMines++;
    }

    public int getNearbyMines() {
        return nearbyMines;
    }

    public boolean isCovered() {
        return state == COVERED || state == SquareState.COVERED_AND_FLAGGED;
    }

    public boolean isFlagable() {
        return state != SquareState.UNCOVERED;
    }

    public boolean isFlagged() {
        return state == SquareState.COVERED_AND_FLAGGED;
    }

    public void flag() {
        state = SquareState.COVERED_AND_FLAGGED;
    }

    public void cover() {
        state = COVERED;
    }

    @Override
    public String toString() {
        switch ( state ) {
            case UNCOVERED:
                if ( mine ) {
                    return "[*]";
                } else {
                    return "[" + nearbyMines + "]";
                }
            case EXPLODED:
                return "[$]";
            case COVERED:
                return "[ ]";
            case COVERED_AND_FLAGGED:
                return "[p]";
        }
        return "";
    }
}
