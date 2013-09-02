package exception;

import java.awt.*;

public class InvalidPositionException extends Exception {

    private Point position;

    public InvalidPositionException (Point position) {
        this.position = position;
    }

    @Override
    public String getMessage() {
        return "Position " + position + " is invalid!";
    }
}
