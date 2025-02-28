package alessandrosalerno.forflex;

import alessandrosalerno.forflex.algebra.ForflexAlgebra;

import java.util.HashMap;
import java.util.Map;

public class ForflexParameterAssignment {
    protected final Map<String, ForflexAlgebra<?>> assignments;
    private final ForflexParameterSpec spec;

    public ForflexParameterAssignment(ForflexParameterSpec spec) {
        this.assignments = new HashMap<>();
        this.spec = spec;
    }

    public ForflexParameterAssignment assign(String name, ForflexAlgebra<?> value) {
        this.assignments.put(name, value);
        return this;
    }

    public ForflexParameterAssignment assignMany(Map<String, ForflexAlgebra<?>> assignments) {
        for (String k : assignments.keySet()) {
            this.assignments.put(k, assignments.get(k));
        }

        return this;
    }

    public ForflexAlgebra<?> get(String name) {
        return this.assignments.get(name);
    }
}
