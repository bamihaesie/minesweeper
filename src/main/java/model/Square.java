package model;

import static model.Square.SquareState.COVERED;
import static model.Square.SquareState.EXPLODED;
import static model.Square.SquareState.UNCOVERED;

public class Square {

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

    public void uncover() {
        this.state = UNCOVERED;
    }

    public void explode() {
        this.state = EXPLODED;
    }

    @Override
    public String toString() {
        if (UNCOVERED.equals(state) && !mine) {
            return "[" + nearbyMines + "]";
        }
        return state.getPrintableSymbol();
    }

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
}
