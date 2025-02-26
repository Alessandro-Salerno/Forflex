package alessandrosalerno.forflex.errors.preprocessor.parser;

import alessandrosalerno.forflex.Token;
import alessandrosalerno.forflex.errors.preprocessor.ForflexPreprocessorError;

public class ForflexParserError extends ForflexPreprocessorError {
    private final String formula;
    private Token token;

    public ForflexParserError(String message, String formula, Token token) {
        super(token.index(), token.line(), token.col(), message);
        this.formula = formula;
        this.token = token;
    }

    @Override
    public void printErrorMessage() {
        System.err.println("" + this.getLine() + ":" + this.getCol() + " ERROR: " + this.getMessage());
        String lineStirng = " " + this.getLine() + " | ";
        String line = this.formula.replaceAll("\r", "").split("\n")[this.getLine() - 1];
        System.err.println(lineStirng + line);
        System.err.println(" ".repeat(lineStirng.length() - 2) + "| " + " ".repeat(this.getCol() - 1 - this.token.value().length()) + "~".repeat(this.token.value().length()));
    }
}
