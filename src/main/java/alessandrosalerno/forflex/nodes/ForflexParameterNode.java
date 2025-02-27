package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.algebra.ForflexAlgebra;

import java.util.Map;

public class ForflexParameterNode implements ForflexEvaluable {
    private final Map<String, ForflexAlgebra> parameters;
    private final String name;

    public ForflexParameterNode(Map<String, ForflexAlgebra> parameters, String name) {
        this.parameters = parameters;
        this.name = name;
    }

    @Override
    public ForflexAlgebra evaluate() {
        return this.parameters.get(name);
    }
}
