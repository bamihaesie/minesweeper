package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private int width;
    private int height;

    private Square[][] matrix;
    private int flagCount;
    private int uncoveredCount;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        regenerateBoard();
    }

    public int getCoveredCount() {
        return width * height - uncoveredCount;
    }

    public int getUncoveredCount() {
        return uncoveredCount;
    }

    public void incrementUncoveredCount() {
        uncoveredCount++;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Square getSquareAtPosition(Point position) {
        if (isValidPosition(position)) {
            return matrix[position.x][position.y];
        }
        return null;
    }

    public void uncoverAllMines() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix[i][j].hasMine()) {
                    matrix[i][j].uncover();
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

    public List<Point> getNeighbours(Point position) {
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
    }

    private void placeEmptySquares() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = new Square(false);
            }
        }
    }

    public boolean isCovered(Point position) {
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

}
