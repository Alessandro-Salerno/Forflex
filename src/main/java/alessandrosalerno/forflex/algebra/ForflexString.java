package alessandrosalerno.forflex.algebra;

import alessandrosalerno.forflex.errors.runtime.ForflexUnsupportedOperationError;

public class ForflexString implements ForflexAlgebra<String> {
    private final String string;

    public ForflexString(String string) {
        this.string = string;
    }

    @Override
    public ForflexAlgebra<String> add(ForflexAlgebra<?> value) {
        return new ForflexString(this.string + value.getPrimitive());
    }

    @Override
    public ForflexAlgebra<String> subtract(ForflexAlgebra<?> value) {
        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.SUBTRACTION, value);
    }

    @Override
    public ForflexAlgebra<String> multiply(ForflexAlgebra<?> value) {
        if (value instanceof ForflexRealNumber timesContainer) {
            double timesR = timesContainer.getPrimitive();
            long timesZ = Math.round(timesR);
            int times = (int) timesZ;
            return new ForflexString(this.string.repeat(times));
        }

        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.MULTIPLICATION, value);
    }

    @Override
    public ForflexAlgebra<String> divide(ForflexAlgebra<?> value) {
        throw new ForflexUnsupportedOperationError(this, ForflexAlgebraOperation.DIVISION, value);
    }

    @Override
    public String getPrimitive() {
        return this.string;
    }
}
