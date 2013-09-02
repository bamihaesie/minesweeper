import exception.ExplosionException;
import org.junit.Before;
import org.junit.Test;

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

    private void assertNumberOfMines(int howMany) {
        // TODO
    }

}
