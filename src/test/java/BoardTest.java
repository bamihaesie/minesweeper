import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Test
    public void testCreateBoard() {
        Board board = new Board(3, 3, 1);
        assertEquals(1, board.getNumberOfMines());
        assertEquals(9, board.getCoveredCount());
        assertEquals(0, board.getUncoveredCount());
    }

}
