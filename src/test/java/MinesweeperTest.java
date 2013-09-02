import exception.ExplosionException;
import exception.InvalidPositionException;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class MinesweeperTest {

    private Minesweeper minesweeper;

    @Before
    public void setUp() {
        minesweeper = new Minesweeper(3, 3, 1);
    }

    @Test
    public void testNumberOfMinesOne() throws ExplosionException {
        assertNumberOfMines(1);
    }

    @Test
    public void testNumberOfMinesTwo() throws ExplosionException {
        minesweeper = new Minesweeper(3, 3, 2);
        assertNumberOfMines(2);
    }

    @Test
    public void testNumberOfMinesFive() throws ExplosionException {
        minesweeper = new Minesweeper(3, 3, 5);
        assertNumberOfMines(5);
    }

    @Test (expected = ExceptionInInitializerError.class)
    public void testTooManyMines() throws ExplosionException {
        minesweeper = new Minesweeper(3, 3, 10);
    }

    @Test
    public void testCreateBoard() {
        assertEquals(9, minesweeper.getCoveredCount());
    }

    @Test
    public void testFlag() throws Exception {
        assertEquals(0, minesweeper.getFlagCount());
        minesweeper.flag(new Point(1, 1));
        assertEquals(1, minesweeper.getFlagCount());
    }

    @Test
    public void testAddFlagAndRemoveFlag() throws Exception {
        assertEquals(0, minesweeper.getFlagCount());
        minesweeper.flag(new Point(1, 1));
        assertEquals(1, minesweeper.getFlagCount());
        minesweeper.flag(new Point(1, 1));
        assertEquals(0, minesweeper.getFlagCount());
    }

    private void assertNumberOfMines(int howMany) {
        // TODO
    }

}
