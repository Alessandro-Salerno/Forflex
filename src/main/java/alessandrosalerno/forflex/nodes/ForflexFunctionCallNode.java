package alessandrosalerno.forflex.nodes;

import alessandrosalerno.forflex.ForflexFunction;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexTypeMismatchError;

import java.util.List;
import java.util.Objects;
import java.util.function.ObjDoubleConsumer;

public class ForflexFunctionCallNode implements ForflexEvaluable {
    private final ForflexFunction function;
    private final List<Object> params;

    public ForflexFunctionCallNode(ForflexFunction function, List<Object> params) {
        this.function = function;
        this.params = params;
    }

    @Override
    public ForflexAlgebra evaluate() {
        Object[] paramResults = new Object[this.params.size()];

        for (int i = 0; i < this.params.size(); i++) {
            Object paramObj = this.params.get(i);

            if (paramObj instanceof ForflexEvaluable param) {
                paramResults[i] = param.evaluate();
            } else {
                paramResults[i] = paramObj;
            }
        }

        return this.function.run(paramResults);
    }
}
