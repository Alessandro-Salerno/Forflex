package alessandrosalerno.forflex;

import alessandrosalerno.forflex.errors.preprocessor.ForflexPreprocessorError;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Lexer l = new Lexer("(a + b + c - d) / (5.47 * sin(1)) + test(\"Ciao + 35 / 3\")");
            List<Token> t = l.tokenize();

            for (Token s : t) {
                System.out.println(s);
            }
        } catch (ForflexPreprocessorError e) {
            e.printErrorMessage();
        }
    }
}