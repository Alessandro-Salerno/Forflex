package alessandrosalerno.forflex.nodes;

public class ForflexIdentity implements ForflexEvaluable {
    private final ForflexAlgebra algebra;

    public ForflexIdentity(ForflexAlgebra algebra) {
        this.algebra = algebra;
    }

    @Override
    public ForflexAlgebra evaluate() {
        return this.algebra;
    }
}
