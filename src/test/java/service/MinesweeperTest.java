package service;

import exception.ExplosionException;
import exception.NoMinesLeftToFindException;
import exception.TooManyMinesException;
import model.Board;
import model.Point;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinesweeperTest {

    private Minesweeper minesweeper;
    private Board board;

    @Before
    public void setUp() throws Exception {
        minesweeper = new Minesweeper(3, 3, 1);
        board = new Board(3, 3, 1, new RandomMinePlacingStrategy());
        minesweeper.setBoard(board);
    }

    @Test (expected = TooManyMinesException.class)
    public void testTooManyMines() throws Exception {
        minesweeper = new Minesweeper(3, 3, 10);
    }

    @Test
    public void testGetAllMinePositions() throws Exception {
        Set<Point> boardMinePositions = board.getAllMinePositions();
        Set<Point> minesweeperMinePositions = minesweeper.getAllMinePositions();
        assertSameSet(boardMinePositions, minesweeperMinePositions);
    }

    @Test (expected = ExplosionException.class)
    public void testClickOnMineCausesExplosion() throws Exception {
        Set<Point> minePositions = board.getAllMinePositions();
        minesweeper.click(minePositions.iterator().next());
    }

    @Test
    public void testClickNextToAMineOnlyUncoversOnePoint() throws Exception {
        board = new Board(2, 2, new Integer[] {0});
        minesweeper.setBoard(board);
        Map<Point, Integer> affectedArea = minesweeper.click(new Point(0, 1));
        assertEquals(1, affectedArea.size());
        assertEquals(1, (int) affectedArea.get(new Point(0, 1)));
    }

    @Test (expected = NoMinesLeftToFindException.class)
    public void testClickNotNextToAMineUncoversAWholeArea() throws Exception {
        board = new Board(3, 3, new Integer[] {0});
        minesweeper.setBoard(board);
        minesweeper.click(new Point(2, 2));
    }

    @Test
    public void testClickNotNextToAMineUncoversAWholeAreaButNotTheWholeBoard() throws Exception {
        board = new Board(3, 5, new Integer[] {6, 8});
        minesweeper.setBoard(board);
        Map<Point, Integer> affectedArea = minesweeper.click(new Point(4, 1));
        assertEquals(6, affectedArea.size());
    }

    private void assertSameSet(Set<Point> expectedMinesList, Set<Point> actualMines) {
        Set<Point> expectedMines = new HashSet<>(expectedMinesList);
        assertEquals("Size is different!", expectedMines.size(), actualMines.size());
        for (Point expectedMine : expectedMines) {
            assertTrue("Contents are not identical! " + expectedMine + " was not found!",
                    actualMines.contains(expectedMine));
        }
    }
}
