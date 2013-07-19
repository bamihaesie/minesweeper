public class Square {

    private SquareState state;

    public Square() {
        state = SquareState.COVERED;
    }

    public SquareState getState() {
        return state;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public void uncover() {
        this.state = SquareState.UNCOVERED;
    }
}
