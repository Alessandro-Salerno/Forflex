package alessandrosalerno.forflex.errors.preprocessor.parser;

import alessandrosalerno.forflex.ForflexToken;

public class ForflexTypeMismatchError extends ForflexParserError {
    public ForflexTypeMismatchError(String formula, ForflexToken token, Class<?> expected, Class<?> got) {
        super("Expected value of type \"" + expected.getSimpleName() + "\", got \"" + got.getSimpleName() + "\"",
                formula, token);
    }
}
