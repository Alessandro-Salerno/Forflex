package alessandrosalerno.forflex.errors.runtime;

public class ForflexParameterTypeError extends RuntimeException {
    public ForflexParameterTypeError(Class<?> expected, Class<?> got, int index) {
        super("Function called with parameter " + index + " of type \"" + got.getSimpleName()
                + "\", expected \"" + expected.getSimpleName() + "\"");
    }
}
