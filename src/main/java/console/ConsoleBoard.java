package console;

import exception.InvalidPositionException;
import model.Point;

import java.util.Map;
import java.util.Set;

class ConsoleBoard {

    private int rows;
    private int columns;
    private GridSquare[][] matrix;

    public ConsoleBoard(int width, int height) {
        this.rows = height;
        this.columns = width;
        regenerateBoard();
    }

    public void flag(Point position) throws InvalidPositionException {
        GridSquare square = getSquareAtPosition(position);
        if (square.isHidden()) {
            square.toggleFlag();
        }
    }

    public void uncoverMines(Set<Point> points) throws InvalidPositionException {
        for (Point point : points) {
            GridSquare square = getSquareAtPosition(point);
            square.toggleHidden();
            square.toggleMine();
        }
    }

    public void uncoverArea(Map<Point, Integer> uncoveredArea) throws InvalidPositionException {
        for (Point point : uncoveredArea.keySet()) {
            Integer nearbyMinesForPoint = uncoveredArea.get(point);
            GridSquare square = getSquareAtPosition(point);
            square.toggleHidden();
            square.setNumberOfMines(nearbyMinesForPoint);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private GridSquare getSquareAtPosition(Point position) throws InvalidPositionException {
        if (!isValidPosition(position)) {
            throw new InvalidPositionException(position);
        }
        return matrix[position.getX()][position.getY()];
    }

    private void regenerateBoard() {
        this.matrix = new GridSquare[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = new GridSquare();
            }
        }
    }

    private boolean isValidPosition(Point position) {
        return position.getX() >= 0 && position.getX() < rows
                && position.getY() >= 0 && position.getY() < columns;
    }
}
