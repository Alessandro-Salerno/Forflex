package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.preprocessor.ForflexPreprocessorError;
import alessandrosalerno.forflex.nodes.ForflexAlgebra;
import alessandrosalerno.forflex.nodes.ForflexEvaluable;
import alessandrosalerno.forflex.nodes.ForflexFunctionCallNode;
import alessandrosalerno.forflex.nodes.ForflexRealNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            String formula = "(a + b + c - d) / (5.47 * sin(1))";
            Map<String, ForflexFunction> functions = new HashMap<>();
            Map<String, ForflexAlgebra> variables = new HashMap<>();

            variables.put("a", new ForflexRealNode(1));
            variables.put("b", new ForflexRealNode(2));
            variables.put("c", new ForflexRealNode(3));
            variables.put("d", new ForflexRealNode(4));

            functions.put("sin", new ForflexFunction() {
                @Override
                public ForflexAlgebra run(ForflexAlgebra[] params) {
                    return new ForflexRealNode(Math.sin(((ForflexRealNode) params[0]).getNumber()));
                }
            });

            ForflexParser parser = new ForflexParser(functions);
            ForflexExpression expr = parser.parse(formula, variables);
            ForflexRealNode result = (ForflexRealNode) expr.evaluate();
            System.out.println(result.getNumber());
        } catch (ForflexPreprocessorError e) {
            e.printErrorMessage();
        }
    }
}