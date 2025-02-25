package alessandrosalerno.forflex.errors.preprocessor.lexer;

import alessandrosalerno.forflex.errors.preprocessor.ForflexPreprocessorError;

public abstract class ForflexLexerError extends ForflexPreprocessorError {
    private String formula;

    public ForflexLexerError(int index, int line, int col, String message, String formula) {
        super(index, line, col, message);
        this.formula = formula;
    }

    @Override
    public void printErrorMessage() {
        System.err.println("" + this.getLine() + ":" + this.getCol() + " ERROR: " + this.getMessage());
        String lineStirng = " " + this.getLine() + " | ";
        String line = this.formula.replaceAll("\r", "").split("\n")[this.getLine() - 1];
        System.err.println(lineStirng + line);
        System.err.println(" ".repeat(lineStirng.length() - 2) + "| " + " ".repeat(this.getCol() - 1) + "^");
    }
}
