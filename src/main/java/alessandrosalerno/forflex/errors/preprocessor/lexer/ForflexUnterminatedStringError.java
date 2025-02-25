package alessandrosalerno.forflex.errors.preprocessor.lexer;

public class ForflexUnterminatedStringError extends ForflexLexerError {
    public ForflexUnterminatedStringError(int index, int line, int col, String formula) {
        super(index, line, col, "Unterminated string at " + line + ":" + col, formula);
    }
}
