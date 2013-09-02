package model;

import exception.InvalidPositionException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private int width;
    private int height;
    private Square[][] matrix;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        regenerateBoard();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Square getSquareAtPosition(Point position) throws InvalidPositionException {
        if (!isValidPosition(position)) {
            throw new InvalidPositionException(position);
        }
        return matrix[position.x][position.y];
    }

    public void uncoverAllMines() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Square currentSquare = matrix[i][j];
                if (currentSquare.hasMine()) {
                    currentSquare.uncover();
                }
            }
        }
    }

    public void flag(Point position) throws InvalidPositionException {
        getSquareAtPosition(position).flag();
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
        this.matrix = new Square[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = new Square(false);
            }
        }
    }

    public boolean isCovered(Point position) throws InvalidPositionException {
        return getSquareAtPosition(position).isCovered();
    }

    private boolean isValidPosition(Point position) {
        return position.x >= 0 && position.x < width
                && position.y >= 0 && position.y < height;
    }

    public boolean isFlagged(Point position) throws InvalidPositionException {
        return getSquareAtPosition(position).isFlagged();
    }

    public boolean canBeFlagged(Point position) throws InvalidPositionException {
        return getSquareAtPosition(position).canBeFlagged();
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
