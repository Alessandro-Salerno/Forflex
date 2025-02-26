package alessandrosalerno.forflex.nodes;

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
    public ForflexAlgebra evaluate() {
        return switch (this.operation) {
            case ADDITION -> this.left.evaluate().add(this.right.evaluate());
            case SUBTRACTION -> this.left.evaluate().subtract(this.right.evaluate());
            case MULTIPLICATION -> this.left.evaluate().multiply(this.right.evaluate());
            case DIVISION -> this.left.evaluate().divide(this.right.evaluate());
        };
    }
}