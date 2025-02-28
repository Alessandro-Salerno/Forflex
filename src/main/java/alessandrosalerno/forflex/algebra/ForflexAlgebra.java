package alessandrosalerno.forflex.algebra;

public interface ForflexAlgebra<PrimitiveType> {
    ForflexAlgebra<PrimitiveType> add(ForflexAlgebra<?> value);
    ForflexAlgebra<PrimitiveType> subtract(ForflexAlgebra<?> value);
    ForflexAlgebra<PrimitiveType> multiply(ForflexAlgebra<?> value);
    ForflexAlgebra<PrimitiveType> divide(ForflexAlgebra<?> value);
    PrimitiveType getPrimitive();
}
