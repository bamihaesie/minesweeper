package model;

import exception.ExplosionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {

    private Square square;

    @Before
    public void setUp() throws Exception {
        square = new Square(false);
    }

    @Test
    public void testCreateSquare() {
        assertTrue(square.isCovered());
    }

    @Test
    public void testSetState() {
        square.flag();
        assertTrue(square.isFlagged());
    }

    @Test
    public void testUncoverSquare() throws ExplosionException {
        square.uncover();
        assertFalse(square.isCovered());
    }

    @Test (expected = ExplosionException.class)
    public void testUncoverSquareWithMine() throws ExplosionException {
        square = new Square(true);
        square.uncover();
    }
}
