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
    private int uncoveredCount;
    private int flagCount;

    public Minesweeper(int width, int height, int numberOfMines) {
        this.numberOfMines = numberOfMines;
        if (numberOfMines > width * height) {
            throw new ExceptionInInitializerError();
        }
        board = new Board(width, height);
        placeMines();
    }

    public void click(Point position) throws ExplosionException, InvalidPositionException, GameOverException {
        uncoverArea(position);
    }

    public void flag(Point position) throws InvalidPositionException {
        if (board.canBeFlagged(position)) {
            if (!board.isFlagged(position)) {
                if (getNumberOfMinesLeft() < 1) {
                    return;
                }
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
        Point randomPosition = generateRandomPosition();
        Square randomSquare = board.getSquareAtPosition(randomPosition);
        if (!randomSquare.hasMine()) {
            randomSquare.setAsMine();
            return randomPosition;
        }
        return null;
    }

    private Point generateRandomPosition() {
        Random random = new Random();
        int randomX = random.nextInt(board.getWidth());
        int randomY = random.nextInt(board.getHeight());
        return new Point(randomX, randomY);
    }

    private void uncoverArea(Point position) throws ExplosionException, GameOverException, InvalidPositionException {
        Square currentSquare = board.getSquareAtPosition(position);
        uncoverSquare(position, currentSquare);

        int nearbyMines = currentSquare.getNearbyMines();
        if (nearbyMines == 0) {
            for (Point neighbour : board.getNeighbours(position)) {
                if (board.isCovered(neighbour)) {
                    uncoverArea(neighbour);
                }
            }
        }
    }

    private void uncoverSquare(Point position, Square currentSquare) throws InvalidPositionException, ExplosionException, GameOverException {
        if (board.isCovered(position)) {
            currentSquare.uncover();
            checkForExplosion(currentSquare);
            incrementUncoveredCount();
            checkForGameTermination();
        }
    }

    private void checkForGameTermination() throws GameOverException {
        if (getCoveredCount() == numberOfMines) {
            throw new GameOverException();
        }
    }

    private void checkForExplosion(Square currentSquare) throws ExplosionException {
        if (currentSquare.hasMine()) {
            currentSquare.explode();
            board.uncoverAllMines();
            throw new ExplosionException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mines left: " + getNumberOfMinesLeft() + "\n");
        sb.append(board.toString());
        return sb.toString();
    }

    private int getNumberOfMinesLeft() {
        return numberOfMines - flagCount;
    }
}
