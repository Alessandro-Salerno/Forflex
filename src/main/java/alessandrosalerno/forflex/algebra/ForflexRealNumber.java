package alessandrosalerno.forflex.algebra;

import alessandrosalerno.forflex.errors.runtime.ForflexUnsupportedOperationError;

public class ForflexRealNumber implements ForflexAlgebra {
    private double doubleValue;

    public ForflexRealNumber(double number) {
        this.doubleValue = number;
    }

    public double getDouble() {
        return this.doubleValue;
    }

    public void setDouble(double number) {
        this.doubleValue = number;
    }

    @Override
    public ForflexAlgebra add(ForflexAlgebra value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue + r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.ADDITION, value);
    }

    @Override
    public ForflexAlgebra subtract(ForflexAlgebra value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue - r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.SUBTRACTION, value);
    }

    @Override
    public ForflexAlgebra multiply(ForflexAlgebra value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue * r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.MULTIPLICATION, value);
    }

    @Override
    public ForflexAlgebra divide(ForflexAlgebra value) {
        if (value instanceof ForflexRealNumber r) {
            return new ForflexRealNumber(this.doubleValue / r.doubleValue);
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.DIVISION, value);
    }
}
