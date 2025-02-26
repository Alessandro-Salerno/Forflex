package alessandrosalerno.forflex.errors.runtime;

public class ForflexParameterCountError extends RuntimeException {
    public ForflexParameterCountError(int expected, int got) {
        super("Expected at least " + expected + " function arguments, got " + got);
    }
}
