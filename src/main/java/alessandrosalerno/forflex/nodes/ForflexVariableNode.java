package alessandrosalerno.forflex.nodes;

import java.util.Map;

public class ForflexVariableNode implements ForflexEvaluable {
    private final String name;
    private final Map<String, ForflexAlgebra> variables;

    public ForflexVariableNode(String name, Map<String, ForflexAlgebra> variables) {
        this.name = name;
        this.variables = variables;
    }

    @Override
    public ForflexAlgebra evaluate() {
        if (!this.variables.containsKey(this.name)) {

        }

        return this.variables.get(this.name);
    }
}
