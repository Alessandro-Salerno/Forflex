package alessandrosalerno.forflex.errors.preprocessor.parser;

import alessandrosalerno.forflex.ForflexToken;

public class ForflexUnexpectedTokenError extends ForflexParserError {
    public ForflexUnexpectedTokenError(String formula, ForflexToken token) {
        super("Unexpected token " + token.type(), formula, token);
    }
}
