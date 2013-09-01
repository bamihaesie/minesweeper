package model;

import exception.ExplosionException;

import static model.Square.SquareState.*;

public class Square {

    enum SquareState {
        COVERED             ("[ ]"),
        COVERED_AND_FLAGGED ("[p]"),
        UNCOVERED           ("[*]"),
        EXPLODED            ("[$]");

        private String printableSymbol;

        SquareState(String printableSymbol) {
            this.printableSymbol = printableSymbol;
        }

        public String getPrintableSymbol() {
            return printableSymbol;
        }
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

    public void setAsMine() {
        this.mine = true;
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
        if (UNCOVERED.equals(state) && !mine) {
            return "[" + nearbyMines + "]";
        }
        return state.getPrintableSymbol();
    }
}
