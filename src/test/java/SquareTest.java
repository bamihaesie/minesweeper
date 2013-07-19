import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareTest {

    @Test
    public void testCreateSquare() {
        Square square = new Square();
        assertEquals(SquareState.COVERED, square.getState());
    }

    @Test
    public void testSetState() {
        Square square = new Square();
        square.setState(SquareState.COVERED_AND_FLAGGED);
        assertEquals(SquareState.COVERED_AND_FLAGGED, square.getState());
    }

    @Test
    public void testUncoverSquare() {
        Square square = new Square();
        square.uncover();
        assertEquals(SquareState.UNCOVERED, square.getState());
    }
}
