package alessandrosalerno.forflex.errors.preprocessor.parser;

import alessandrosalerno.forflex.Token;

public class ForflexTypeMismatchError extends ForflexParserError {
    public ForflexTypeMismatchError(String formula, Token token, Class<?> expected, Class<?> got) {
        super("Expected value of type \"" + expected.getSimpleName() + "\", got \"" + got.getSimpleName() + "\"",
                formula, token);
    }
}
