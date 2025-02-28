package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.algebra.ForflexAlgebra;

public class ForflexIdentityNode implements ForflexEvaluable {
    private final ForflexAlgebra<?> algebra;

    public ForflexIdentityNode(ForflexAlgebra<?> algebra) {
        this.algebra = algebra;
    }

    @Override
    public ForflexAlgebra<?> evaluate() {
        return this.algebra;
    }
}
