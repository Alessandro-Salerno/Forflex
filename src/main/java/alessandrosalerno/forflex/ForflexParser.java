package alessandrosalerno.forflex;

/*
Grammar system:
    [stuff] -> stuff is optional
    <stuff> -> stuff is a non-terminal
    stuff -> stuff is a terminal
    {stuff}* -> repeat stuff as many times as you want >= 0 (e.g., stuffstuffstuff)
    foo|bar -> either foo or bar
Grammar:
    <symbol>     := anything in UTF-8
    <digit>      := 0|1|2|3|4|5|6|7|8|9
    <integer>    := <digit>{<digit>}*
    <num>        := <integer>[.<integer>]
    <lowercase>  := a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z
    <uppercase>  := A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z
    <letter>     := <lowercase>|<uppercase>
    <string>     := "{<symbol>}*"
    <identifier> := <letter>|_{<letter>|<digit>|_}*
    <expr>       := <term>[{+|-}<term>]
    <params>     := [<expr>{, <expr>}*]
    <call>       := <identifier>(<params>)
    <term>       := <factor>[{*|/}<factor>]
    <factor>     := -<factor>|(<expr>)|<number>|<identifier>|<call>
 */

import alessandrosalerno.forflex.errors.preprocessor.lexer.ForflexUnexpectedCharacterError;
import alessandrosalerno.forflex.errors.preprocessor.lexer.ForflexUnterminatedStringError;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexParserError;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexTypeMismatchError;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexUndeclaredIdentifierError;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexUnexpectedTokenError;
import alessandrosalerno.forflex.nodes.*;

import java.lang.reflect.Array;
import java.util.*;

public class ForflexParser {
    private final Map<String, ForflexFunction> functions;

    public ForflexParser(Map<String, ForflexFunction> functions) {
        this.functions = functions;
    }

    public ForflexParser() {
        this.functions = new HashMap<>();
    }

    public ForflexParser addFunction(String name, ForflexFunction function) {
        this.functions.put(name, function);
        return this;
    }

    public ForflexParser addFunctions(Map<String, ForflexFunction> functions) {
        return this;
    }

    public ForflexExpression parse(String formula, Map<String, ForflexAlgebra> variables) throws ForflexUnterminatedStringError, ForflexUnexpectedCharacterError, ForflexTypeMismatchError, ForflexUnexpectedTokenError, ForflexUndeclaredIdentifierError {
        Lexer l = new Lexer(formula);
        List<Token> tokens = l.tokenize();
        ExpressionParser parser = new ExpressionParser(tokens, this.functions, variables, formula, 0);
        ForflexEvaluable root = parser.parseExpression();
        return new ForflexExpression(root);
    }

    private static class ExpressionParser {
        private final List<Token> tokens;
        private final Map<String, ForflexFunction> functions;
        private final Map<String, ForflexAlgebra> variables;
        private final String formula;
        private int index;

        public ExpressionParser(List<Token> tokens, Map<String, ForflexFunction> functions, Map<String, ForflexAlgebra> variables, String formula, int index) {
            this.tokens = tokens;
            this.functions = functions;
            this.variables = variables;
            this.formula = formula;
            this.index = index;
        }

        private ForflexEvaluable parseExpression() throws ForflexUnexpectedTokenError, ForflexTypeMismatchError, ForflexUndeclaredIdentifierError {
            ForflexEvaluable expr = this.parseTerm();
            Token next = null;

            while (null != (next = this.nextTokenOfType(TokenType.PLUS, TokenType.MINUS))) {
                ForflexAlgebraOperation op = switch (next.type()) {
                    case PLUS -> ForflexAlgebraOperation.ADDITION;
                    case MINUS -> ForflexAlgebraOperation.SUBTRACTION;
                    default -> null;
                };

                expr = new ForflexBinaryNode(expr, this.parseTerm(), op);
            }

            return expr;
        }

        private ForflexEvaluable parseTerm() throws ForflexUnexpectedTokenError, ForflexTypeMismatchError, ForflexUndeclaredIdentifierError {
            ForflexEvaluable term = this.parseFactor();
            Token next = null;

            while (null != (next = this.nextTokenOfType(TokenType.STAR, TokenType.SLASH))) {
                ForflexAlgebraOperation op = switch (next.type()) {
                    case STAR -> ForflexAlgebraOperation.MULTIPLICATION;
                    case SLASH -> ForflexAlgebraOperation.DIVISION;
                    default -> null;
                };

                term = new ForflexBinaryNode(term, this.parseFactor(), op);
            }

            return term;
        }

        private ForflexEvaluable parseFactor() throws ForflexUnexpectedTokenError, ForflexUndeclaredIdentifierError, ForflexTypeMismatchError {
            Token token = this.expect(TokenType.LPAREN, TokenType.MINUS, TokenType.NUMBER, TokenType.IDENTIFIER);

            switch (token.type()) {
                case LPAREN -> {
                    ForflexEvaluable ret = this.parseExpression();
                    this.expect(TokenType.RPAREN);
                    return ret;
                }

                case MINUS -> {
                    ForflexEvaluable subFactor = this.parseFactor();
                    return new ForflexBinaryNode(new ForflexIdentity(new ForflexRealNode(-1)), subFactor, ForflexAlgebraOperation.MULTIPLICATION);
                }

                case NUMBER -> {
                    return new ForflexIdentity(new ForflexRealNode(Double.parseDouble(token.value())));
                }

                case IDENTIFIER -> {
                    if (!this.functions.containsKey(token.value())
                            && !this.variables.containsKey(token.value())) {
                        throw new ForflexUndeclaredIdentifierError(this.formula, token);
                    }

                    if (TokenType.LPAREN == this.peekToken(TokenType.LPAREN)) {
                        if (this.variables.containsKey(token.value())) {
                            throw new ForflexTypeMismatchError(this.formula, token, ForflexFunction.class, ForflexAlgebra.class);
                        }

                        ForflexFunction function = this.functions.get(token.value());
                        return new ForflexFunctionCallNode(function, this.parseParameterList());
                    }

                    if (this.functions.containsKey(token.value())) {
                        throw new ForflexTypeMismatchError(this.formula, token, ForflexAlgebra.class, ForflexFunction.class);
                    }

                    return new ForflexIdentity(this.variables.get(token.value()));
                }
            }

            // TODO: is this right?
            return null;
        }

        private List<ForflexEvaluable> parseParameterList() throws ForflexUnexpectedTokenError, ForflexTypeMismatchError, ForflexUndeclaredIdentifierError {
            this.expect(TokenType.LPAREN);
            List<ForflexEvaluable> params = new ArrayList<>();
            Token next = null;

            do {
                // TODO: handle strings
                params.add(this.parseExpression());
                next = this.expect(TokenType.COMMA, TokenType.RPAREN);
            } while (TokenType.COMMA == next.type());

            return params;
        }

        private TokenType peekToken(TokenType... types) {
            return this.tokens.get(this.index).type();
        }

        private Token nextTokenOfType(TokenType... types) {
            if (this.index >= this.tokens.size()) {
                return null;
            }

            Token next = this.tokens.get(this.index);

            if (Arrays.asList(types).contains(next.type())) {
                this.index++;
                return next;
            }

            return null;
        }

        private Token expect(TokenType... types) throws ForflexUnexpectedTokenError {
            Token ret = this.nextTokenOfType(types);

            if (null == ret) {
                throw new ForflexUnexpectedTokenError(this.formula, this.tokens.get(this.index));
            }

            return ret;
        }
    }
}
