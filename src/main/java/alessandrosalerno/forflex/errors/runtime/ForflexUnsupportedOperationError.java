package alessandrosalerno.forflex.errors.runtime;

import alessandrosalerno.forflex.nodes.ForflexAlgebra;
import alessandrosalerno.forflex.nodes.ForflexAlgebraOperation;

public class ForflexUnsupportedOperationError extends RuntimeException {
    public ForflexUnsupportedOperationError(ForflexAlgebra a, ForflexAlgebraOperation operation, ForflexAlgebra b) {
        super("Algebra \"" + a.getClass().getSimpleName()
                + "\" does not support operation \"" + operation + "\" with argument of " +
                "algebra \"" + b.getClass().getSimpleName() + "\"" );
    }
}
