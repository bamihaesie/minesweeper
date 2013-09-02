package model;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(3, 3);
    }

    @Test
    public void testCreateBoard() {
        assertEquals(9, board.getCoveredCount());
        assertEquals(0, board.getUncoveredCount());
    }

    @Test
    public void testFlag() {
        assertEquals(0, board.getFlagCount());
        board.flag(1, 1);
        assertEquals(1, board.getFlagCount());
    }

    @Test
    public void testFlagTwice() {
        assertEquals(0, board.getFlagCount());
        board.flag(1, 1);
        board.flag(1, 1);
        assertEquals(1, board.getFlagCount());
    }

    @Test
    public void testFlagAndUnflag() {
        assertEquals(0, board.getFlagCount());
        board.flag(1, 1);
        assertEquals(1, board.getFlagCount());
        board.unflag(1, 1);
        assertEquals(0, board.getFlagCount());
    }

    @Test
    public void testUnflagDoesNotWorkBeforeFlag() {
        assertEquals(0, board.getFlagCount());
        board.unflag(1, 1);
        assertEquals(0, board.getFlagCount());
    }

    @Test
    public void testNeighbours() {
        board = new Board(3, 3);
        assertEquals(3, board.getNeighbours(new Point(0, 0)).size());
        assertEquals(5, board.getNeighbours(new Point(0, 1)).size());
        assertEquals(8, board.getNeighbours(new Point(1, 1)).size());
    }

}
