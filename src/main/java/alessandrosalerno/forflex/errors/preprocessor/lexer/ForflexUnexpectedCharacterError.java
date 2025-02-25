package alessandrosalerno.forflex.errors.preprocessor.lexer;

public class ForflexUnexpectedCharacterError extends ForflexLexerError {
    public ForflexUnexpectedCharacterError(int index, int line, int col, char c, String formula) {
        super(index, line, col, "Unexpected character '" + c + "' at " + line + ":" + col, formula);
    }
}
