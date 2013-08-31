import model.Board;
import model.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Minesweeper {

    private Board board;

    public Minesweeper(int width, int height, int numberOfMines) {
        board = new Board(width, height, numberOfMines);
    }

    public void click(Point position) {
        try {
            board.uncover(position);
            System.out.println(board);
        } catch (Exception e) {
            board.uncoverAllMines();
            System.out.println(e.getMessage());
            System.out.println(board);
            System.exit(0);
        }
    }

    private void flag(Point position) {
        board.flag(position);
        System.out.println(board);
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to Minesweeper");
        try {
        System.out.println("Please select board size (w/h):");
        String line = br.readLine();
        int width = Integer.parseInt(line.split("/")[0]);
        int height = Integer.parseInt(line.split("/")[1]);

        System.out.println("Please select number of mines: ");
        line = br.readLine();
        int numberOfMines = Integer.parseInt(line);

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
                minesweeper.flag(position);
            } else {
                minesweeper.click(position);
            }
        }

        } catch (IOException e) {

        }

    }



}
