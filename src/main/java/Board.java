import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {

    private int width;
    private int height;
    private int numberOfMines;
    private Square[][] matrix;
    private int flagCount;
    private int uncoveredCount;

    public Board(int width, int height, int numberOfMines) {
        this.width = width;
        this.height = height;
        this.numberOfMines = numberOfMines;
        if (numberOfMines > width * height) {
            throw new ExceptionInInitializerError();
        }
        regenerateBoard();
    }

    public int getCoveredCount() {
        return width * height - uncoveredCount;
    }

    public int getUncoveredCount() {
        return uncoveredCount;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void uncover(Point position) throws ExplosionException, GameOverException {
        if (isValidPosition(position)) {
            uncoverAndExpandEmptyArea(position);
        }
    }

    public void uncoverAllMines() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix[i][j].hasMine()) {
                    matrix[i][j].setState(SquareState.UNCOVERED);
                }
            }
        }
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void flag(Point position) {
        if (isValidPosition(position)) {
            if (!isFlagged(position) && isFlagable(position)) {
                flagCount++;
                matrix[position.getX()][position.getY()].setState(SquareState.COVERED_AND_FLAGGED);
            }
        }
    }

    public void unflag(Point position) {
        if (isValidPosition(position)) {
            if (isFlagged(position)) {
                flagCount--;
                matrix[position.getX()][position.getY()].setState(SquareState.COVERED);
            }
        }
    }

    protected List<Point> getNeighbours(Point position) {
        List<Point> neighbours = new ArrayList<Point>();
        int[] xSkew = new int[] {0, 1, 1, 1, 0, -1, -1, -1};
        int[] ySkew = new int[] {1, 1, 0, -1, -1, -1, 0, 1};
        for (int i = 0; i < xSkew.length; i++) {
            Point neighbour = new Point(position.getX() + xSkew[i], position.getY() + ySkew[i]);
            if (isValidPosition(neighbour)) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private void regenerateBoard() {
        uncoveredCount = 0;
        this.matrix = new Square[width][height];
        placeEmptySquares();
        placeMines();
    }

    private void placeMines() {
        for (int i = 0; i < numberOfMines; i++) {
            Point position = placeRandomMine();
            while (position == null) {
                position = placeRandomMine();
            }
            updateNeighbours(position);
        }
    }

    private void updateNeighbours(Point position) {
        for (Point neighbour : getNeighbours(position)) {
            Square square = matrix[neighbour.getX()][neighbour.getY()];
            square.incrementNearbyMines();
        }
    }

    private Point placeRandomMine() {
        Random random = new Random();
        int randomX = random.nextInt(width);
        int randomY = random.nextInt(height);
        if (!matrix[randomX][randomY].hasMine()) {
            matrix[randomX][randomY].setMine(true);
            return new Point(randomX, randomY);
        }
        return null;
    }

    private void placeEmptySquares() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = new Square(false);
            }
        }
    }

    private void uncoverAndExpandEmptyArea(Point position) throws ExplosionException, GameOverException {
        if (isCovered(position)) {
            matrix[position.getX()][position.getY()].uncover();
            uncoveredCount++;
            if (getCoveredCount() == numberOfMines) {
                throw new GameOverException();
            }
        }
        int nearbyMines = matrix[position.getX()][position.getY()].getNearbyMines();
        if (nearbyMines == 0) {
            for (Point neighbour : getNeighbours(position)) {
                if (isCovered(neighbour)) {
                    uncoverAndExpandEmptyArea(neighbour);
                }
            }
        }
    }

    private boolean isCovered(Point position) {
        SquareState state = matrix[position.getX()][position.getY()].getState();
        return state == SquareState.COVERED || state == SquareState.COVERED_AND_FLAGGED;
    }

    private boolean isValidPosition(Point position) {
        return position.getX() >= 0 && position.getX() < width
                && position.getY() >= 0 && position.getY() < height;
    }

    private boolean isFlagged(Point position) {
        return matrix[position.getX()][position.getY()].getState() == SquareState.COVERED_AND_FLAGGED;
    }

    private boolean isFlagable(Point position) {
        return matrix[position.getX()][position.getY()].getState() != SquareState.UNCOVERED;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
