package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.ForflexParameterAssignment;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;

public class ForflexIdentityNode implements ForflexEvaluable {
    private final ForflexAlgebra<?> algebra;

    public ForflexIdentityNode(ForflexAlgebra<?> algebra) {
        this.algebra = algebra;
    }

    @Override
    public ForflexAlgebra<?> evaluate(ForflexParameterAssignment params) {
        return this.algebra;
    }
}
