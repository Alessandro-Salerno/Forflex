package alessandrosalerno.forflex.errors.preprocessor;

public abstract class ForflexPreprocessorError extends Exception {
    private final int index;
    private final int line;
    private final int col;

    protected ForflexPreprocessorError(int index, int line, int col, String message) {
        super(message);
        this.index = index;
        this.line = line;
        this.col = col;
    }

    public int getIndex() {
        return this.index;
    }

    public int getLine() {
        return this.line;
    }

    public int getCol() {
        return this.col;
    }

    public abstract void printErrorMessage();
}
