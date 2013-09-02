import exception.ExplosionException;
import exception.GameOverException;
import exception.InvalidPositionException;
import model.Board;
import model.Square;

import java.awt.*;
import java.util.Random;

public class Minesweeper {

    private Board board;
    private int numberOfMines;
    private int flagCount;
    private int uncoveredCount;

    public Minesweeper(int width, int height, int numberOfMines) {
        this.numberOfMines = numberOfMines;
        if (numberOfMines > width * height) {
            throw new ExceptionInInitializerError();
        }
        board = new Board(width, height);
        placeMines();
    }

    public void click(Point position) throws ExplosionException, InvalidPositionException, GameOverException {
        uncoverAndExpandEmptyArea(position);
    }

    public void flag(Point position) throws InvalidPositionException {
        if (board.canBeFlagged(position)) {
            if (!board.isFlagged(position)) {
                flagCount++;
            } else {
                flagCount--;
            }
            board.flag(position);
        }
    }

    public int getFlagCount() {
        return flagCount;
    }

    public int getCoveredCount() {
        return board.getWidth() * board.getHeight() - uncoveredCount;
    }

    public void incrementUncoveredCount() {
        uncoveredCount++;
    }

    private void placeMines() {
        try {
            for (int i = 0; i < numberOfMines; i++) {
                Point position = placeRandomMine();
                while (position == null) {
                    position = placeRandomMine();
                }
                updateNeighbours(position);
            }
        } catch (InvalidPositionException ignore) {}
    }

    private void updateNeighbours(Point position) throws InvalidPositionException {
        for (Point neighbour : board.getNeighbours(position)) {
            Square neighbourSquare = board.getSquareAtPosition(neighbour);
            neighbourSquare.incrementNearbyMines();
        }
    }

    private Point placeRandomMine() throws InvalidPositionException {
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

    private void uncoverAndExpandEmptyArea(Point position) throws ExplosionException, GameOverException, InvalidPositionException {
        if (board.isCovered(position)) {
            Square squareAtPosition = board.getSquareAtPosition(position);
            squareAtPosition.uncover();
            if (squareAtPosition.hasMine()) {
                squareAtPosition.explode();
                board.uncoverAllMines();
                throw new ExplosionException();
            }
            incrementUncoveredCount();
            if (getCoveredCount() == numberOfMines) {
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
