import exception.InvalidPositionException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleRunner {

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

            Point position = new Point(x, y);

            if (flag) {
                try {
                    minesweeper.flag(position);
                    System.out.println(minesweeper);
                } catch (InvalidPositionException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                try {
                    minesweeper.click(position);
                    System.out.println(minesweeper);
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
