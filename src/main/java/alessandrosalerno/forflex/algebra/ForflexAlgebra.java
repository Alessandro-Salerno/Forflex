package alessandrosalerno.forflex.algebra;

public interface ForflexAlgebra<PrimitiveType> {
    ForflexAlgebra<?> add(ForflexAlgebra<?> value);
    ForflexAlgebra<?> subtract(ForflexAlgebra<?> value);
    ForflexAlgebra<?> multiply(ForflexAlgebra<?> value);
    ForflexAlgebra<?> divide(ForflexAlgebra<?> value);
    PrimitiveType getPrimitive();
}
