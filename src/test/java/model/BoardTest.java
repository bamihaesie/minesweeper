package model;

import exception.ExplosionException;
import exception.GameOverException;
import model.Board;
import model.Point;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(3, 3, 1);
    }

    @Test
    public void testCreateBoard() {
        assertEquals(1, board.getNumberOfMines());
        assertEquals(9, board.getCoveredCount());
        assertEquals(0, board.getUncoveredCount());
    }

    @Test
    public void testUncoverInvalidPosition() throws ExplosionException, GameOverException {
        board.uncover(new Point(-1, 1));
        assertEquals(9, board.getCoveredCount());
        assertEquals(0, board.getUncoveredCount());
    }

    @Test
    public void testFlag() throws ExplosionException {
        assertEquals(0, board.getFlagCount());
        board.flag(new Point(1, 1));
        assertEquals(1, board.getFlagCount());
    }

    @Test
    public void testFlagTwice() throws ExplosionException {
        assertEquals(0, board.getFlagCount());
        board.flag(new Point(1, 1));
        board.flag(new Point(1, 1));
        assertEquals(1, board.getFlagCount());
    }

    @Test
    public void testFlagAndUnflag() throws ExplosionException {
        assertEquals(0, board.getFlagCount());
        board.flag(new Point(1, 1));
        assertEquals(1, board.getFlagCount());
        board.unflag(new Point(1, 1));
        assertEquals(0, board.getFlagCount());
    }

    @Test
    public void testUnflagDoesNotWorkBeforeFlag() throws ExplosionException {
        assertEquals(0, board.getFlagCount());
        board.unflag(new Point(1, 1));
        assertEquals(0, board.getFlagCount());
    }

    @Test
    public void testNumberOfMinesOne() throws ExplosionException {
        assertNumberOfMines(1);
    }

    @Test
    public void testNumberOfMinesTwo() throws ExplosionException {
        board = new Board(3, 3, 2);
        assertNumberOfMines(2);
    }

    @Test
    public void testNumberOfMinesFive() throws ExplosionException {
        board = new Board(3, 3, 5);
        assertNumberOfMines(5);
    }

    @Test (expected = ExceptionInInitializerError.class)
    public void testTooManyMines() throws ExplosionException {
        board = new Board(3, 3, 10);
    }

    @Test
    public void testNeighbours() {
        board = new Board(3, 3, 5);
        assertEquals(3, board.getNeighbours(new Point(0, 0)).size());
        assertEquals(5, board.getNeighbours(new Point(0, 1)).size());
        assertEquals(8, board.getNeighbours(new Point(1, 1)).size());
    }

    private void assertNumberOfMines(int howMany) {
        int found = 0;
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                try {
                    board.uncover(new Point(i, j));
                } catch (ExplosionException e) {
                    found++;
                } catch (GameOverException e) {

                }
            }
        }
        assertEquals(howMany, found);
    }

}
