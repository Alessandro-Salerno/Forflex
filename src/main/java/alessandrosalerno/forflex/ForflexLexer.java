package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.preprocessor.lexer.ForflexUnexpectedCharacterError;
import alessandrosalerno.forflex.errors.preprocessor.lexer.ForflexUnterminatedStringError;

import java.util.ArrayList;
import java.util.List;

public class ForflexLexer {
    private final String formula;
    private int next;
    private int line;
    private int column;

    public ForflexLexer(String formula) {
        this.formula = formula;
        this.next = 0;
        this.line = 1;
        this.column = 1;
    }

    public List<ForflexToken> tokenize() throws ForflexUnterminatedStringError, ForflexUnexpectedCharacterError {
        List<ForflexToken> tokens = new ArrayList<>();
        ForflexToken t = null;

        while (ForflexTokenType.EOF != (t = this.tokenizeNext()).type()) {
            tokens.add(t);
        }

        tokens.add(new ForflexToken(ForflexTokenType.EOF, " ", this.line, this.line, this.column));
        return tokens;
    }

    private ForflexToken tokenizeNext() throws ForflexUnterminatedStringError, ForflexUnexpectedCharacterError {
        ForflexTokenType tokenType = ForflexTokenType.EOF;
        StringBuilder val = new StringBuilder();
        boolean isString = false;
        char last = 0;
        int beginIndex = this.next;
        int beginLine = this.line;
        int beginCol = this.column;
        char c = 0;

        for (; this.next < this.formula.length(); last = c, this.advance()) {
            c = this.formula.charAt(this.next);

            // handle spaces outside strings
            if (!isString && Character.isSpaceChar(c)) {
                if (val.isEmpty()) {
                    continue;
                }

                break;
                // Unreachable
            }

            // Ignore \r
            if ('\r' == c) {
                this.next++;
                this.column--;
                continue;
                // Unreachable
            }

            // Handle new lines
            if ('\n' == c) {
                // New lines are not allowed inside strings
                if (isString) {
                    throw new ForflexUnterminatedStringError(this.next, this.line, this.column, this.formula);
                }

                this.newLine();
                continue;
                // Unreachable
            }

            // Handle quotes (strings)
            if ('"' == c) {
                // If this is a new strig
                if (!isString) {
                    if (!val.isEmpty()) {
                        throw new ForflexUnexpectedCharacterError(this.next, this.line, this.column, c, this.formula);
                    }

                    isString = true;
                    tokenType = ForflexTokenType.STRING;

                    // Avoid adding the quotes to the string
                    continue;
                    // Unreachable
                } else if ('\\' != last) {
                    // Close the string if it was open AND it's not \"
                    isString = false;
                    this.advance();
                    break;
                    // Unreachable
                }
            }

            // While insiade a string
            if (isString) {
                // Add every character that's not a backslah
                // Backslashes can be added with \\ (last == c)
                if ('\\' != c || (last == c)) {
                    val.append(c);
                }

                continue;
                // Unreachable
            }

            ForflexTokenType candidate = switch (c) {
                case '+' -> ForflexTokenType.PLUS;
                case '-' -> ForflexTokenType.MINUS;
                case '*' -> ForflexTokenType.STAR;
                case '/' -> ForflexTokenType.SLASH;
                case '(' -> ForflexTokenType.LPAREN;
                case ')' -> ForflexTokenType.RPAREN;
                case ',' -> ForflexTokenType.COMMA;

                default -> null;
            };

            if (null != candidate) {
                if (!val.isEmpty()) {
                    break;
                }

                tokenType = candidate;
                val.append("."); // Dummy char for length eval
                this.advance();
                break;
            }

            if (val.isEmpty()) {
                if (Character.isDigit(c)) {
                    tokenType = ForflexTokenType.NUMBER;
                } else if ('_' == c || Character.isAlphabetic(c)) {
                    tokenType = ForflexTokenType.IDENTIFIER;
                } else {
                    throw new ForflexUnexpectedCharacterError(this.next, this.line, this.column, c, this.formula);
                }
            }

            if (ForflexTokenType.NUMBER == tokenType) {
                if ('.' == c && val.toString().contains(".")) {
                    throw new ForflexUnexpectedCharacterError(this.next, this.line, this.column, c, this.formula);
                }

                if (!Character.isDigit(c) && '.' != c) {
                    throw new ForflexUnexpectedCharacterError(this.next, this.line, this.column, c, this.formula);
                }

                val.append(c);
            }

            if (ForflexTokenType.IDENTIFIER == tokenType) {
                if ('_' != c && !Character.isAlphabetic(c) && !Character.isDigit(c)) {
                    throw new ForflexUnexpectedCharacterError(this.next, this.line, this.column, c, this.formula);
                }

                val.append(c);
            }
        }

        if (isString) {
            throw new ForflexUnterminatedStringError(this.next, this.line, this.column, this.formula);
        }

        return new ForflexToken(tokenType, val.toString(), beginIndex, beginLine, beginCol);
    }

    private void advance() {
        this.next++;
        this.column++;
    }

    private void newLine() {
        this.line++;
        this.column = 0;
    }
}
