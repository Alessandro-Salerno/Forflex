package alessandrosalerno.forflex.errors.preprocessor.parser;

import alessandrosalerno.forflex.ForflexToken;

public class ForflexUndeclaredIdentifierError extends ForflexParserError {
    public ForflexUndeclaredIdentifierError(String formula, ForflexToken token) {
        super("Undeclared identifier \"" + token.value() + "\"", formula, token);
    }
}
