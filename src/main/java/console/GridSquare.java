package console;

public class GridSquare {

    private boolean hidden;
    private boolean flag;
    private boolean mine;
    private int numberOfMines;

    public GridSquare() {
        hidden = true;
        flag = false;
        mine = false;
        numberOfMines = -1;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void toggleHidden() {
        hidden = !hidden;
    }

    public void toggleFlag() {
        flag = !flag;
    }

    public void toggleMine() {
        mine = !mine;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    @Override
    public String toString() {
        String label;
        if (hidden) {
            if (flag) {
                label = "p";
            } else {
                label = "*";
            }
        } else {
            if (mine) {
                label = "$";
            } else {
                label = numberOfMines == 0 ? " " : Integer.toString(numberOfMines);
            }
        }
        return "[" + label + "]";
    }

}
