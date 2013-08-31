package exception;

public class GameOverException extends Exception {

    @Override
    public String getMessage() {
        return "Congratulations! You won!";
    }
}
