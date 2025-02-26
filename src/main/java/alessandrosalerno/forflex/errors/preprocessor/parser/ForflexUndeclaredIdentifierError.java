package alessandrosalerno.forflex.errors.preprocessor.parser;

import alessandrosalerno.forflex.Token;

public class ForflexUndeclaredIdentifierError extends ForflexParserError {
    public ForflexUndeclaredIdentifierError(String formula, Token token) {
        super("Undeclared identifier \"" + token.value() + "\"", formula, token);
    }
}
