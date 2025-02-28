package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.ForflexParameterAssignment;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;

public interface ForflexEvaluable {
    ForflexAlgebra<?> evaluate(ForflexParameterAssignment parameters);
}
