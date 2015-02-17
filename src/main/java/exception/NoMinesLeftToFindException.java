package exception;

public class NoMinesLeftToFindException extends GameOverException {

    @Override
    public String getMessage() {
        return "You won!";
    }
}
