package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.ForflexFunction;
import alessandrosalerno.forflex.ForflexParameterAssignment;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;

import java.util.List;

public class ForflexFunctionCallNode implements ForflexEvaluable {
    private final ForflexFunction<?> function;
    private final List<ForflexEvaluable> arguments;

    public ForflexFunctionCallNode(ForflexFunction<?> function, List<ForflexEvaluable> arguments) {
        this.function = function;
        this.arguments = arguments;
    }

    @Override
    public ForflexAlgebra<?> evaluate(ForflexParameterAssignment params) {
        ForflexAlgebra<?>[] argResults = new ForflexAlgebra<?>[this.arguments.size()];

        for (int i = 0; i < this.arguments.size(); i++) {
            argResults[i] = this.arguments.get(i).evaluate(params);
        }

        return this.function.run(argResults);
    }
}
