package alessandrosalerno.forflex.algebra;

import alessandrosalerno.forflex.errors.runtime.ForflexUnsupportedOperationError;

public class ForflexRealNumber implements ForflexAlgebra<Double> {
    private final double doubleValue;

    public ForflexRealNumber(double number) {
        this.doubleValue = number;
    }

    @Override
    public Double getPrimitive() {
        return this.doubleValue;
    }

    @Override
    public ForflexRealNumber add(ForflexAlgebra<?> value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue + r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.ADDITION, value);
    }

    @Override
    public ForflexRealNumber subtract(ForflexAlgebra<?> value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue - r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.SUBTRACTION, value);
    }

    @Override
    public ForflexRealNumber multiply(ForflexAlgebra<?> value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue * r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.MULTIPLICATION, value);
    }

    @Override
    public ForflexRealNumber divide(ForflexAlgebra<?> value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue / r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.DIVISION, value);
    }
}
