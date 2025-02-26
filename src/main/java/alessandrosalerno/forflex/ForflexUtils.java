package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexTypeMismatchError;
import alessandrosalerno.forflex.errors.runtime.ForflexParameterCountError;
import alessandrosalerno.forflex.errors.runtime.ForflexParameterTypeException;
import alessandrosalerno.forflex.nodes.ForflexAlgebra;

import java.util.HashMap;
import java.util.Map;

public class ForflexUtils {
    private static class DefaultFunctions extends HashMap<String, ForflexFunction> {
        private DefaultFunctions() {

        }
    }

    public static Map<String, ForflexFunction> DEFAULT_FUNCTIONS = new DefaultFunctions();

    public static <T> T requireParameterType(Object[] params, int index, Class<T> clazz) {
        if (index >= params.length) {
            throw new ForflexParameterCountError(index, params.length);
        }

        if (clazz.isInstance(params[index])) {
            return clazz.cast(params[index]);
        }

        throw new ForflexParameterTypeException(clazz, params[index].getClass(), index);
    }
}
