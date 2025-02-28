package alessandrosalerno.forflex.errors.runtime;

public class ForflexArgumentCountError extends RuntimeException {
    public ForflexArgumentCountError(int expected, int got) {
        super("Expected at least " + expected + " function arguments, got " + got);
    }
}
