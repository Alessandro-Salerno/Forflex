package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.ForflexParameterAssignment;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;

import java.util.Map;

public class ForflexParameterNode implements ForflexEvaluable {
    private final String name;

    public ForflexParameterNode(String name) {
        this.name = name;
    }

    @Override
    public ForflexAlgebra<?> evaluate(ForflexParameterAssignment params) {
        return params.get(name);
    }
}
