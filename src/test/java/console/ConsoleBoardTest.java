package console;

import model.Point;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ConsoleBoardTest {

    private ConsoleBoard board;

    @Test
    public void testDefaultBoard() throws Exception {
        board = new ConsoleBoard(2, 2);
        assertEquals("[*] [*] \n" +
                     "[*] [*] \n", board.toString());
    }

    @Test
    public void testFlag() throws Exception {
        board = new ConsoleBoard(2, 2);
        board.flag(new Point(0, 0));
        assertEquals("[p] [*] \n" +
                     "[*] [*] \n", board.toString());
    }

    @Test
    public void testRemoveFlag() throws Exception {
        board = new ConsoleBoard(2, 2);
        board.flag(new Point(0, 0));
        board.flag(new Point(0, 0));
        assertEquals("[*] [*] \n" +
                     "[*] [*] \n", board.toString());
    }

    @Test
    public void testUncoverAreaWithNearbyMines() throws Exception {
        board = new ConsoleBoard(2, 2);
        Map<Point, Integer> uncoveredArea = new HashMap<>();
        uncoveredArea.put(new Point(0, 0), 1);
        board.uncoverArea(uncoveredArea);
        assertEquals("[1] [*] \n" +
                     "[*] [*] \n", board.toString());
    }

    @Test
    public void testUncoverAreaWithNoNearbyMines() throws Exception {
        board = new ConsoleBoard(2, 2);
        Map<Point, Integer> uncoveredArea = new HashMap<>();
        uncoveredArea.put(new Point(0, 0), 0);
        board.uncoverArea(uncoveredArea);
        assertEquals("[ ] [*] \n" +
                "[*] [*] \n", board.toString());
    }

    @Test
    public void testUncoverMines() throws Exception {
        board = new ConsoleBoard(2, 2);
        Set<Point> mines = new HashSet<>();
        mines.add(new Point(0, 0));
        board.uncoverMines(mines);
        assertEquals("[$] [*] \n" +
                "[*] [*] \n", board.toString());
    }
}