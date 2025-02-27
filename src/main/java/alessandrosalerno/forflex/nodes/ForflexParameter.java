package alessandrosalerno.forflex.nodes;

import java.util.Map;

public class ForflexParameter implements ForflexEvaluable {
    private final Map<String, ForflexAlgebra> parameters;
    private final String name;

    public ForflexParameter(Map<String, ForflexAlgebra> parameters, String name) {
        this.parameters = parameters;
        this.name = name;
    }

    @Override
    public ForflexAlgebra evaluate() {
        return this.parameters.get(name);
    }
}
