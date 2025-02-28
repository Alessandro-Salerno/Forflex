package alessandrosalerno.forflex;

import java.util.HashSet;
import java.util.Set;

public class ForflexParameterSpec {
    private final Set<String> parameterNames;

    public ForflexParameterSpec() {
        this.parameterNames = new HashSet<>();
    }

    public ForflexParameterSpec addParam(String name) {
        this.parameterNames.add(name);
        return this;
    }

    public ForflexParameterSpec addParams(Set<String> params) {
        for (String k : params) {
            this.parameterNames.add(k);
        }

        return this;
    }

    public boolean validateAssignment(ForflexParameterAssignment assignment) {
        for (String k : this.parameterNames) {
            if (!assignment.assignments.containsKey(k)) {
                return false;
            }
        }

        return true;
    }

    public boolean exists(String name) {
        return this.parameterNames.contains(name);
    }
}
