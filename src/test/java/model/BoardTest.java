package model;

import exception.TooManyMinesException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RandomMinePlacingStrategy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BoardTest {

    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    @Mock
    private RandomMinePlacingStrategy randomMinePlacingStrategy;

    private Board board;
    private int numberOfMines = 3;

    @Test (expected = TooManyMinesException.class)
    public void testTooManyMines() throws Exception {
        board = new Board(WIDTH, HEIGHT, WIDTH * HEIGHT + 1, randomMinePlacingStrategy);
    }

    @Test
    public void testGetNeighbours() throws Exception {
        when(randomMinePlacingStrategy.generatePlacement(WIDTH * HEIGHT, numberOfMines)).thenReturn(new Integer[] {0, 1, 2});
        board = new Board(WIDTH, HEIGHT, numberOfMines, randomMinePlacingStrategy);

        Set<Point> actualNeighbours = board.getNeighbours(new Point(0, 0));

        List<Point> expectedNeighbours = Arrays.asList(new Point(0, 1), new Point(1, 0), new Point(1, 1));
        assertEquals(expectedNeighbours.size(), actualNeighbours.size());
        assertSameSet(expectedNeighbours, actualNeighbours);

        assertEquals(5, board.getNeighbours(new Point(0, 1)).size());
        assertEquals(8, board.getNeighbours(new Point(1, 1)).size());
    }

    @Test
    public void testPlaceMinesFirstRow() throws Exception {
        when(randomMinePlacingStrategy.generatePlacement(WIDTH * HEIGHT, numberOfMines)).thenReturn(new Integer[] {0, 1, 2});
        board = new Board(WIDTH, HEIGHT, numberOfMines, randomMinePlacingStrategy);
        List<Point> expectedMines = Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(0, 2));
        int[][] expectedCounts = new int[][] {
                {1, 2, 1},
                {2, 3, 2},
                {0, 0, 0}
        };
        assertSameSet(expectedMines, board.getAllMinePositions());
        checkNearbyMines(WIDTH, HEIGHT, expectedCounts);
    }

    @Test
    public void testPlaceMinesDiagonal() throws Exception {
        when(randomMinePlacingStrategy.generatePlacement(WIDTH * HEIGHT, numberOfMines)).thenReturn(new Integer[] {0, 4, 8});
        board = new Board(WIDTH, HEIGHT, numberOfMines, randomMinePlacingStrategy);
        List<Point> expectedMines = Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(2, 2));
        int[][] expectedCounts = new int[][] {
                {1, 2, 1},
                {2, 2, 2},
                {1, 2, 1}
        };
        assertSameSet(expectedMines, board.getAllMinePositions());
        checkNearbyMines(WIDTH, HEIGHT, expectedCounts);
    }

    @Test
    public void testGetUncoveredCount() throws Exception {
        when(randomMinePlacingStrategy.generatePlacement(WIDTH * HEIGHT, numberOfMines)).thenReturn(new Integer[] {0, 1, 2});
        board = new Board(WIDTH, HEIGHT, numberOfMines, randomMinePlacingStrategy);
        assertEquals(WIDTH * HEIGHT, board.getCoveredCount());
        board.uncover(new Point(1, 0));
        assertEquals(WIDTH * HEIGHT - 1, board.getCoveredCount());
    }

    private void assertSameSet(List<Point> expectedMinesList, Set<Point> actualMines) {
        Set<Point> expectedMines = new HashSet<>(expectedMinesList);
        assertEquals("Size is different!", expectedMines.size(), actualMines.size());
        for (Point expectedMine : expectedMines) {
            assertTrue("Contents are not identical! " + expectedMine + " was not found!",
                    actualMines.contains(expectedMine));
        }
    }

    private void checkNearbyMines(int width, int height, int[][] expectedCounts) throws Exception {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Point currentPoint = new Point(i, j);
                assertEquals(expectedCounts[i][j], board.getNearbyMines(currentPoint));
            }
        }
    }

}
