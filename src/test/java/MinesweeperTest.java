import exception.ExplosionException;
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

    @Test (expected = ExceptionInInitializerError.class)
    public void testTooManyMines() throws ExplosionException {
        minesweeper = new Minesweeper(3, 3, 10);
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

    @Test
    public void testCannotPutMoreFlagsThanMines() throws Exception {
        assertEquals(0, minesweeper.getFlagCount());
        minesweeper.flag(new Point(0, 1));
        assertEquals(1, minesweeper.getFlagCount());
        minesweeper.flag(new Point(1, 1));
        assertEquals(1, minesweeper.getFlagCount());
    }

}
