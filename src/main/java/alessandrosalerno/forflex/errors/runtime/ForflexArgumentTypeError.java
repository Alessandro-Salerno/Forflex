package alessandrosalerno.forflex.errors.runtime;

public class ForflexArgumentTypeError extends RuntimeException {
    public ForflexArgumentTypeError(Class<?> expected, Class<?> got, int index) {
        super("Function called with parameter " + index + " of type \"" + got.getSimpleName()
                + "\", expected \"" + expected.getSimpleName() + "\"");
    }
}
