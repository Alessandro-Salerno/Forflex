package alessandrosalerno.forflex.errors.runtime;

public class ForflexUnassignedParametersError extends RuntimeException {
    public ForflexUnassignedParametersError() {
        super("One or more expression parameters are declared but never assigned");
    }
}
