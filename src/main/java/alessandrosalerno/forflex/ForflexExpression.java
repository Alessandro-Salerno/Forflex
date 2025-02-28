package alessandrosalerno.forflex;

import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.nodes.ForflexBinaryNode;
import alessandrosalerno.forflex.nodes.ForflexEvaluable;

public class ForflexExpression implements ForflexEvaluable {
    private final ForflexEvaluable root;

    public ForflexExpression() {
        this.root = new ForflexBinaryNode();
    }

    public ForflexExpression(ForflexEvaluable root) {
        this.root = root;
    }

    @SuppressWarnings("unused")
    public ForflexEvaluable getRoot() {
        return this.root;
    }

    @Override
    public ForflexAlgebra<?> evaluate() {
        return this.root.evaluate();
    }
}
