package console;

import org.junit.Test;

import static org.junit.Assert.*;

public class GridSquareTest {

    private GridSquare square;

    @Test
    public void testHiddenSquare() throws Exception {
        square = new GridSquare();
        assertEquals("[*]", square.toString());
    }

    @Test
    public void testFlaggedSquare() throws Exception {
        square = new GridSquare();
        square.toggleFlag();
        assertEquals("[p]", square.toString());
    }

    @Test
    public void testMineSquare() throws Exception {
        square = new GridSquare();
        square.toggleHidden();
        square.toggleMine();
        assertEquals("[$]", square.toString());
    }

    @Test
    public void testNumberSquare() throws Exception {
        square = new GridSquare();
        square.toggleHidden();
        square.setNumberOfMines(1);
        assertEquals("[1]", square.toString());
    }

    @Test
    public void testNumberZeroSquare() throws Exception {
        square = new GridSquare();
        square.toggleHidden();
        square.setNumberOfMines(0);
        assertEquals("[ ]", square.toString());
    }
}