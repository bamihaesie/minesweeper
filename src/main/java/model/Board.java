package model;

import exception.InvalidPositionException;
import exception.TooManyMinesException;
import service.RandomMinePlacingStrategy;

import java.util.HashSet;
import java.util.Set;

public class Board {

    private int rows;
    private int columns;
    private int numberOfMines;
    private Square[][] matrix;

    public Board(int width, int height, int numberOfMines, RandomMinePlacingStrategy randomMinePlacingStrategy) throws TooManyMinesException {
        this.rows = height;
        this.columns = width;
        this.numberOfMines = numberOfMines;
        regenerateBoard();
        if (numberOfMines > rows * columns) {
            throw new TooManyMinesException();
        }
        Integer[] placement = randomMinePlacingStrategy.generatePlacement(rows * columns, numberOfMines);
        placeMines(placement);
    }

    public Board(int width, int height, Integer[] minePlacement) throws TooManyMinesException {
        this.rows = height;
        this.columns = width;
        this.numberOfMines = minePlacement.length;
        regenerateBoard();
        if (numberOfMines > rows * columns) {
            throw new TooManyMinesException();
        }
        placeMines(minePlacement);
    }

    public Set<Point> getAllMinePositions() {
        Set<Point> mines = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Square currentSquare = matrix[i][j];
                if (currentSquare.hasMine()) {
                    mines.add(new Point(i, j));
                }
            }
        }
        return mines;
    }

    public Set<Point> getNeighbours(Point position) {
        Set<Point> neighbours = new HashSet<>();
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

    public boolean isCovered(Point position) throws InvalidPositionException {
        return getSquareAtPosition(position).isCovered();
    }

    public void uncover(Point position) throws InvalidPositionException {
        getSquareAtPosition(position).uncover();
    }

    public boolean isMine(Point position) throws InvalidPositionException {
        return getSquareAtPosition(position).hasMine();
    }

    public int getNearbyMines(Point position) throws InvalidPositionException {
        return getSquareAtPosition(position).getNearbyMines();
    }

    private Square getSquareAtPosition(Point position) {
        return matrix[position.getX()][position.getY()];
    }

    private void placeMines(Integer[] placement) {
        for (int i = 0; i < numberOfMines; i++) {
            Integer currentMine = placement[i];
            Point position = new Point(currentMine / columns, currentMine % columns);
            getSquareAtPosition(position).setAsMine();
            updateNeighbours(position);
        }
    }

    private void updateNeighbours(Point position) {
        for (Point neighbour : getNeighbours(position)) {
            Square neighbourSquare = getSquareAtPosition(neighbour);
            neighbourSquare.incrementNearbyMines();
        }
    }

    private void regenerateBoard() {
        this.matrix = new Square[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new Square(false);
            }
        }
    }

    private boolean isValidPosition(Point position) {
        return position.getX() >= 0 && position.getX() < rows
                && position.getY() >= 0 && position.getY() < columns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                sb.append(matrix[j][i]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int getCoveredCount() {
        int coveredCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j].isCovered()) {
                    coveredCount++;
                }
            }
        }
        return coveredCount;
    }
}
