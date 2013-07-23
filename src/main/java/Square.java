public class Square {

    private SquareState state;
    private boolean mine;
    private int nearbyMines;
    private boolean exploded;

    public Square(boolean mine) {
        this.mine = mine;
        state = SquareState.COVERED;
        nearbyMines = 0;
    }

    public SquareState getState() {
        return state;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public boolean hasMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void uncover() throws ExplosionException {
        this.state = SquareState.UNCOVERED;
        if (hasMine()) {
            exploded = true;
            throw new ExplosionException();
        }
    }

    public void incrementNearbyMines() {
        nearbyMines++;
    }

    public int getNearbyMines() {
        return nearbyMines;
    }

    public boolean isExploded() {
        return exploded;
    }
}
