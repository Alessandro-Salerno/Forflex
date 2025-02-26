package alessandrosalerno.forflex.errors.runtime;

public class ForflexParameterTypeException extends RuntimeException {
    public ForflexParameterTypeException(Class<?> expected, Class<?> got, int index) {
        super("Function called with parameter " + index + " of type \"" + got.getSimpleName()
                + "\", expected \"" + expected.getSimpleName() + "\"");
    }
}
