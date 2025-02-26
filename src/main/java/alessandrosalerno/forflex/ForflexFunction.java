package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexTypeMismatchError;
import alessandrosalerno.forflex.nodes.ForflexAlgebra;

import java.util.Map;

public interface ForflexFunction {
    ForflexAlgebra run(Object[] params);
}
