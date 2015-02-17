package console;

import exception.ExplosionException;
import exception.InvalidPositionException;
import exception.NoMinesLeftToFindException;
import model.Point;
import service.Minesweeper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

public class ConsoleRunner {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to service.Minesweeper");

        System.out.println("Please select board size (w/h):");
        String line = br.readLine();
        int width = Integer.parseInt(line.split("/")[0]);
        int height = Integer.parseInt(line.split("/")[1]);

        System.out.println("Please select number of mines: ");
        line = br.readLine();
        int numberOfMines = Integer.parseInt(line);

        ConsoleBoard board = new ConsoleBoard(width, height);

        Minesweeper minesweeper = new Minesweeper(width, height, numberOfMines);

        while (true) {
            System.out.println("Your move (x/y): ");
            line = br.readLine();
            if ("exit".equals(line)) {
                System.exit(-1);
            }
            boolean flag = false;
            if (line.startsWith("flag ")) {
                line = line.substring(5);
                flag = true;
            }
            int x = Integer.parseInt(line.split("/")[0]);
            int y = Integer.parseInt(line.split("/")[1]);

            Point position = new Point(x, y);

            if (flag) {
                try {
                    board.flag(position);
                    System.out.println(board);
                } catch (InvalidPositionException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    Map<Point, Integer> uncoveredArea = minesweeper.click(position);
                    board.uncoverArea(uncoveredArea);
                    System.out.println(board);
                } catch (ExplosionException e) {
                    Set<Point> minePositions = minesweeper.getAllMinePositions();
                    board.uncoverMines(minePositions);
                    System.out.println(board);
                    System.out.println(e.getMessage());
                    return;
                } catch (NoMinesLeftToFindException e) {
                    System.out.println(board);
                    System.out.println(e.getMessage());
                } catch (InvalidPositionException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println(minesweeper);
                    System.exit(0);
                }
            }
        }

    }

}


