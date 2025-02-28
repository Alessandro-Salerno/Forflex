package alessandrosalerno.forflex;

import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.errors.runtime.ForflexUnassignedParametersError;
import alessandrosalerno.forflex.nodes.ForflexBinaryNode;
import alessandrosalerno.forflex.nodes.ForflexEvaluable;

public class ForflexExpression implements ForflexEvaluable {
    private final ForflexEvaluable root;
    private final ForflexParameterSpec parameterSpec;

    public ForflexExpression() {
        this.root = new ForflexBinaryNode();
        this.parameterSpec = new ForflexParameterSpec();
    }

    public ForflexExpression(ForflexEvaluable root, ForflexParameterSpec parameterSpec) {
        this.root = root;
        this.parameterSpec = parameterSpec;
    }

    @SuppressWarnings("unused")
    public ForflexEvaluable getRoot() {
        return this.root;
    }

    @SuppressWarnings("unused")
    public ForflexParameterSpec getParameterSpec() {
        return parameterSpec;
    }

    @Override
    public ForflexAlgebra<?> evaluate(ForflexParameterAssignment params) {
        if (!this.parameterSpec.validateAssignment(params)) {
            throw new ForflexUnassignedParametersError();
        }

        return this.root.evaluate(params);
    }
}
