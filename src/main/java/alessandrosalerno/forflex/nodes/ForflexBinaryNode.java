package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.ForflexParameterAssignment;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.algebra.ForflexAlgebraOperation;

public class ForflexBinaryNode implements ForflexEvaluable {
    private ForflexEvaluable left;
    private ForflexEvaluable right;
    private ForflexAlgebraOperation operation;

    public ForflexBinaryNode(ForflexEvaluable left, ForflexEvaluable right, ForflexAlgebraOperation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public ForflexBinaryNode() {
        this.left = null;
        this.right = null;
        this.operation = null;
    }

    @Override
    public ForflexAlgebra evaluate(ForflexParameterAssignment params) {
        return switch (this.operation) {
            case ADDITION -> this.left.evaluate(params).add(this.right.evaluate(params));
            case SUBTRACTION -> this.left.evaluate(params).subtract(this.right.evaluate(params));
            case MULTIPLICATION -> this.left.evaluate(params).multiply(this.right.evaluate(params));
            case DIVISION -> this.left.evaluate(params).divide(this.right.evaluate(params));
        };
    }
}