package model;

import exception.ExplosionException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {

    private Square emptySquare;
    private Square minedSquare;

    @Before
    public void setUp() throws Exception {
        emptySquare = new Square(false);
        minedSquare = new Square(true);
    }

    @Test
    public void testHasMine() {
        assertFalse(emptySquare.hasMine());
        assertTrue(minedSquare.hasMine());
    }

    @Test
    public void testSetAsMineWhenNotAMine() {
        assertFalse(emptySquare.hasMine());
        emptySquare.setAsMine();
        assertTrue(emptySquare.hasMine());
    }

    @Test
    public void testSetAsMineWhenAlreadyAMine() {
        assertTrue(minedSquare.hasMine());
        minedSquare.setAsMine();
        assertTrue(minedSquare.hasMine());
    }

    @Test
    public void testUncoverSquare() throws ExplosionException {
        assertTrue(emptySquare.isCovered());
        emptySquare.uncover();
        assertFalse(emptySquare.isCovered());
    }

    @Test
    public void testNearbyMines() {
        assertEquals(0, emptySquare.getNearbyMines());
        emptySquare.incrementNearbyMines();
        assertEquals(1, emptySquare.getNearbyMines());
        emptySquare.incrementNearbyMines();
        assertEquals(2, emptySquare.getNearbyMines());
    }

}
