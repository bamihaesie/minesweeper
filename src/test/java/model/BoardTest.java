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
    public void testNeighbours() {
        board = new Board(3, 3);
        assertEquals(3, board.getNeighbours(new Point(0, 0)).size());
        assertEquals(5, board.getNeighbours(new Point(0, 1)).size());
        assertEquals(8, board.getNeighbours(new Point(1, 1)).size());
    }

}
