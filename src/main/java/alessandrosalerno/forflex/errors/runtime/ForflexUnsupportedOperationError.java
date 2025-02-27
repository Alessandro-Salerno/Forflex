package alessandrosalerno.forflex.errors.runtime;

import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.algebra.ForflexAlgebraOperation;

public class ForflexUnsupportedOperationError extends RuntimeException {
    public ForflexUnsupportedOperationError(ForflexAlgebra a, ForflexAlgebraOperation operation, ForflexAlgebra b) {
        super("Algebra \"" + a.getClass().getSimpleName()
                + "\" does not support operation \"" + operation + "\" with argument of " +
                "algebra \"" + b.getClass().getSimpleName() + "\"" );
    }
}
