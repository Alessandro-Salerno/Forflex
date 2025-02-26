package alessandrosalerno.forflex.errors.preprocessor.parser;

import alessandrosalerno.forflex.Token;

public class ForflexUnexpectedTokenError extends ForflexParserError {
    public ForflexUnexpectedTokenError(String formula, Token token) {
        super("Unexpected token " + token.type(), formula, token);
    }
}
