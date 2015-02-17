package service;

import exception.*;
import model.Board;
import model.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Minesweeper {

    private Board board;
    private int numberOfMines;

    public Minesweeper(int width, int height, int numberOfMines) throws TooManyMinesException {
        this.numberOfMines = numberOfMines;
        board = new Board(width, height, numberOfMines, new RandomMinePlacingStrategy());
    }

    public Map<Point, Integer> click(Point position) throws GameOverException, InvalidPositionException {
        Map<Point, Integer> affectedArea = new HashMap<>();
        uncoverArea(position, affectedArea);
        return affectedArea;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private void uncoverArea(Point position, Map<Point, Integer> affectedArea) throws GameOverException, InvalidPositionException {
        if (board.isCovered(position)) {
            board.uncover(position);
            if (noMinesLeftToFind()) {
                throw new NoMinesLeftToFindException();
            }

            if (affectedArea.containsKey(position)) {
                affectedArea.put(position, affectedArea.get(position) + 1);
            } else {
                affectedArea.put(position, 1);
            }

            if (board.isMine(position)) {
                throw new ExplosionException();
            }
        }

        int nearbyMines = board.getNearbyMines(position);
        if (nearbyMines == 0) {
            for (Point neighbour : board.getNeighbours(position)) {
                if (board.isCovered(neighbour)) {
                    uncoverArea(neighbour, affectedArea);
                }
            }
        }
    }

    public Set<Point> getAllMinePositions() {
        return board.getAllMinePositions();
    }

    private boolean noMinesLeftToFind() {
        return board.getCoveredCount() - numberOfMines == 0;
    }
}
