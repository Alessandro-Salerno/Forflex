package alessandrosalerno.forflex;

import alessandrosalerno.forflex.algebra.ForflexAlgebra;

public interface ForflexFunction<ReturnType extends ForflexAlgebra<?>> {
    ReturnType run(ForflexAlgebra<?>[] args);
}
