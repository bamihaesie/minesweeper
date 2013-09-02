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
    public void testCreateSquare() {
        assertTrue(emptySquare.isCovered());
        assertEquals(0, emptySquare.getNearbyMines());
        assertFalse(emptySquare.hasMine());
        assertTrue(minedSquare.hasMine());
    }

    @Test
    public void testAddFlagRemoveFlag() {
        assertFalse(emptySquare.isFlagged());
        emptySquare.flag();
        assertTrue(emptySquare.isFlagged());
        emptySquare.flag();
        assertFalse(emptySquare.isFlagged());
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
