package model;

import exception.ExplosionException;
import model.Square;
import model.SquareState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareTest {

    private Square square;

    @Before
    public void setUp() throws Exception {
        square = new Square(false);
    }

    @Test
    public void testCreateSquare() {
        assertEquals(SquareState.COVERED, square.getState());
    }

    @Test
    public void testSetState() {
        square.setState(SquareState.COVERED_AND_FLAGGED);
        assertEquals(SquareState.COVERED_AND_FLAGGED, square.getState());
    }

    @Test
    public void testUncoverSquare() throws ExplosionException {
        square.uncover();
        assertEquals(SquareState.UNCOVERED, square.getState());
    }

    @Test (expected = ExplosionException.class)
    public void testUncoverSquareWithMine() throws ExplosionException {
        square = new Square(true);
        square.uncover();
    }
}
