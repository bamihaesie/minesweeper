package exception;

public class TooManyMinesException extends Exception {

    @Override
    public String getMessage() {
        return "Too many mines! Not enough room on the board!";
    }
}
