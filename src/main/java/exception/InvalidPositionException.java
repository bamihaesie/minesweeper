package exception;

import model.Point;

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
