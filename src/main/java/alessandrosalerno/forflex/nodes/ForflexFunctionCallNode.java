package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.ForflexFunction;

import java.util.List;

public class ForflexFunctionCallNode implements ForflexEvaluable {
    private final ForflexFunction function;
    private final List<ForflexEvaluable> params;

    public ForflexFunctionCallNode(ForflexFunction function, List<ForflexEvaluable> params) {
        this.function = function;
        this.params = params;
    }

    @Override
    public ForflexAlgebra evaluate() {
        ForflexAlgebra[] paramResults = new ForflexAlgebra[this.params.size()];

        for (int i = 0; i < this.params.size(); i++) {
            paramResults[i] = this.params.get(i).evaluate();
        }

        return this.function.run(paramResults);
    }
}
