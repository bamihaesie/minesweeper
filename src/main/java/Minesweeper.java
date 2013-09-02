import exception.ExplosionException;
import exception.GameOverException;
import model.Board;
import model.Square;

import java.awt.*;
import java.util.Random;

public class Minesweeper {

    private Board board;
    private int numberOfMines;

    public Minesweeper(int width, int height, int numberOfMines) {
        this.numberOfMines = numberOfMines;
        if (numberOfMines > width * height) {
            throw new ExceptionInInitializerError();
        }
        board = new Board(width, height);
        placeMines();
    }

    public void click(int x, int y) throws Exception {
        uncoverAndExpandEmptyArea(new Point(x, y));
    }

    public void flag(int x, int y) {
        board.flag(x, y);
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
        for (Point neighbour : board.getNeighbours(position)) {
            Square neighbourSquare = board.getSquareAtPosition(neighbour);
            neighbourSquare.incrementNearbyMines();
        }
    }

    private Point placeRandomMine() {
        Random random = new Random();
        int randomX = random.nextInt(board.getWidth());
        int randomY = random.nextInt(board.getHeight());
        Square randomSquare = board.getSquareAtPosition(new Point(randomX, randomY));
        if (!randomSquare.hasMine()) {
            randomSquare.setAsMine();
            return new Point(randomX, randomY);
        }
        return null;
    }

    private void uncoverAndExpandEmptyArea(Point position) throws ExplosionException, GameOverException {
        if (board.isCovered(position)) {
            Square squareAtPosition = board.getSquareAtPosition(position);
            squareAtPosition.uncover();
            if (squareAtPosition.hasMine()) {
                squareAtPosition.explode();
                board.uncoverAllMines();
                throw new ExplosionException();
            }
            board.incrementUncoveredCount();
            if (board.getCoveredCount() == numberOfMines) {
                throw new GameOverException();
            }
        }
        int nearbyMines = board.getSquareAtPosition(position).getNearbyMines();
        if (nearbyMines == 0) {
            for (Point neighbour : board.getNeighbours(position)) {
                if (board.isCovered(neighbour)) {
                    uncoverAndExpandEmptyArea(neighbour);
                }
            }
        }
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
