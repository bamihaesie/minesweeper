import model.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Minesweeper {

    private Board board;

    public Minesweeper(int width, int height, int numberOfMines) {
        board = new Board(width, height, numberOfMines);
    }

    public void click(int x, int y) {
        try {
            board.uncover(x, y);
            System.out.println(board);
        } catch (Exception e) {
            board.uncoverAllMines();
            System.out.println(e.getMessage());
            System.out.println(board);
            System.exit(0);
        }
    }

    public void flag(int x, int y) {
        board.flag(x, y);
        System.out.println(board);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to Minesweeper");

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

            if (flag) {
                minesweeper.flag(x, y);
            } else {
                minesweeper.click(x, y);
            }
        }

    }

}
