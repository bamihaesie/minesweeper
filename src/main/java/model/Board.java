package model;

import exception.ExplosionException;
import exception.GameOverException;

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

    public void uncover(int x, int y) throws ExplosionException, GameOverException {
        Point position = new Point(x, y);
        if (isValidPosition(position)) {
            uncoverAndExpandEmptyArea(position);
        }
    }

    public void uncoverAllMines() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix[i][j].hasMine()) {
                    try {
                        matrix[i][j].uncover();
                    } catch (ExplosionException ignore) { }
                }
            }
        }
    }

    public int getFlagCount() {
        return flagCount;
    }

    public void flag(int x, int y) {
        Point position = new Point(x, y);
        if (isValidPosition(position)) {
            if (!isFlagged(position) && isFlagable(position)) {
                flagCount++;
                matrix[position.x][position.y].flag();
            }
        }
    }

    public void unflag(int x, int y) {
        Point position = new Point(x, y);
        if (isValidPosition(position)) {
            if (isFlagged(position)) {
                flagCount--;
                matrix[position.x][position.y].cover();
            }
        }
    }

    protected List<Point> getNeighbours(int x, int y) {
        Point position = new Point(x, y);
        List<Point> neighbours = new ArrayList<Point>();
        int[] xSkew = new int[] {0, 1, 1, 1, 0, -1, -1, -1};
        int[] ySkew = new int[] {1, 1, 0, -1, -1, -1, 0, 1};
        for (int i = 0; i < xSkew.length; i++) {
            Point neighbour = new Point(position.x + xSkew[i], position.y + ySkew[i]);
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
        for (Point neighbour : getNeighbours(position.x, position.y)) {
            Square square = matrix[neighbour.x][neighbour.y];
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
            matrix[position.x][position.y].uncover();
            uncoveredCount++;
            if (getCoveredCount() == numberOfMines) {
                throw new GameOverException();
            }
        }
        int nearbyMines = matrix[position.x][position.y].getNearbyMines();
        if (nearbyMines == 0) {
            for (Point neighbour : getNeighbours(position.x, position.y)) {
                if (isCovered(neighbour)) {
                    uncoverAndExpandEmptyArea(neighbour);
                }
            }
        }
    }

    private boolean isCovered(Point position) {
        Square s = matrix[position.x][position.y];
        return s.isCovered();
    }

    private boolean isValidPosition(Point position) {
        return position.x >= 0 && position.x < width
                && position.y >= 0 && position.y < height;
    }

    private boolean isFlagged(Point position) {
        return matrix[position.x][position.y].isFlagged();
    }

    private boolean isFlagable(Point position) {
        return matrix[position.x][position.y].isFlagable();
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

    class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ')';
        }
    }
}
