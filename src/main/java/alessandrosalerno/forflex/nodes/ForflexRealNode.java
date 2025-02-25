package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.errors.runtime.ForflexUnsupportedOperationError;

public class ForflexRealNode implements ForflexAlgebra {
    private double number;

    public ForflexRealNode(double number) {
        this.number = number;
    }

    public double getNumber() {
        return this.number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    @Override
    public ForflexAlgebra add(ForflexAlgebra value) {
        if (value instanceof ForflexRealNode r) {
            return new ForflexRealNode(this.number + r.number);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.ADDITION, value);
    }

    @Override
    public ForflexAlgebra subtract(ForflexAlgebra value) {
        if (value instanceof ForflexRealNode r) {
            return new ForflexRealNode(this.number - r.number);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.SUBTRACTION, value);
    }

    @Override
    public ForflexAlgebra multiply(ForflexAlgebra value) {
        if (value instanceof ForflexRealNode r) {
            return new ForflexRealNode(this.number * r.number);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.MULTIPLICATION, value);
    }

    @Override
    public ForflexAlgebra divide(ForflexAlgebra value) {
        if (value instanceof ForflexRealNode r) {
            return new ForflexRealNode(this.number / r.number);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.DIVISION, value);
    }
}
