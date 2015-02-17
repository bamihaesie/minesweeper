package exception;

public class ExplosionException extends GameOverException {

    @Override
    public String getMessage() {
        return "BOOM!";
    }
}
