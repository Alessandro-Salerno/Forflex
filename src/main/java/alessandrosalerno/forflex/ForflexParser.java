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

import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.algebra.ForflexAlgebraOperation;
import alessandrosalerno.forflex.algebra.ForflexRealNumber;
import alessandrosalerno.forflex.errors.preprocessor.lexer.ForflexUnexpectedCharacterError;
import alessandrosalerno.forflex.errors.preprocessor.lexer.ForflexUnterminatedStringError;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexTypeMismatchError;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexUndeclaredIdentifierError;
import alessandrosalerno.forflex.errors.preprocessor.parser.ForflexUnexpectedTokenError;
import alessandrosalerno.forflex.nodes.*;

import java.util.*;

public class ForflexParser {
    private final Map<String, ForflexFunction> functions;

    public ForflexParser() {
        this.functions = new HashMap<>();
    }

    public ForflexParser addFunction(String name, ForflexFunction function) {
        this.functions.put(name, function);
        return this;
    }

    public ForflexParser addFunctions(Map<String, ForflexFunction> functions) {
        for (String s : functions.keySet()) {
            this.functions.put(s, functions.get(s));
        }

        return this;
    }

    public ForflexExpression parse(String formula, Map<String, ForflexAlgebra> parameters) throws ForflexUnterminatedStringError, ForflexUnexpectedCharacterError, ForflexTypeMismatchError, ForflexUnexpectedTokenError, ForflexUndeclaredIdentifierError {
        ForflexLexer l = new ForflexLexer(formula);
        List<ForflexToken> tokens = l.tokenize();
        ExpressionParser parser = new ExpressionParser(tokens, this.functions, parameters, formula, 0);
        ForflexEvaluable root = parser.parseAll();
        return new ForflexExpression(root);
    }

    private static class ExpressionParser {
        private final List<ForflexToken> tokens;
        private final Map<String, ForflexFunction> functions;
        private final Map<String, ForflexAlgebra> parameters;
        private final String formula;
        private int index;

        public ExpressionParser(List<ForflexToken> tokens, Map<String, ForflexFunction> functions, Map<String, ForflexAlgebra> variables, String formula, int index) {
            this.tokens = tokens;
            this.functions = functions;
            this.parameters = variables;
            this.formula = formula;
            this.index = index;
        }

        private ForflexEvaluable parseAll() throws ForflexTypeMismatchError, ForflexUnexpectedTokenError, ForflexUndeclaredIdentifierError {
            ForflexEvaluable formula = this.parseExpression();
            this.expect(ForflexTokenType.EOF);
            return formula;
        }

        private ForflexEvaluable parseExpression() throws ForflexUnexpectedTokenError, ForflexTypeMismatchError, ForflexUndeclaredIdentifierError {
            ForflexEvaluable expr = this.parseTerm();
            ForflexToken next = null;

            while (null != (next = this.nextTokenOfType(ForflexTokenType.PLUS, ForflexTokenType.MINUS))) {
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
            ForflexToken next = null;

            while (null != (next = this.nextTokenOfType(ForflexTokenType.STAR, ForflexTokenType.SLASH))) {
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
            ForflexToken token = this.expect(ForflexTokenType.LPAREN, ForflexTokenType.MINUS, ForflexTokenType.NUMBER, ForflexTokenType.IDENTIFIER);

            switch (token.type()) {
                case LPAREN -> {
                    ForflexEvaluable ret = this.parseExpression();
                    this.expect(ForflexTokenType.RPAREN);
                    return ret;
                }

                case MINUS -> {
                    ForflexEvaluable subFactor = this.parseFactor();
                    return new ForflexBinaryNode(new ForflexIdentityNode(new ForflexRealNumber(-1)), subFactor, ForflexAlgebraOperation.MULTIPLICATION);
                }

                case NUMBER -> {
                    return new ForflexIdentityNode(new ForflexRealNumber(Double.parseDouble(token.value())));
                }

                case IDENTIFIER -> {
                    if (!this.functions.containsKey(token.value())
                            && !this.parameters.containsKey(token.value())) {
                        throw new ForflexUndeclaredIdentifierError(this.formula, token);
                    }

                    if (ForflexTokenType.LPAREN == this.peekToken(ForflexTokenType.LPAREN)) {
                        if (this.parameters.containsKey(token.value())) {
                            throw new ForflexTypeMismatchError(this.formula, token, ForflexFunction.class, ForflexAlgebra.class);
                        }

                        ForflexFunction function = this.functions.get(token.value());
                        return new ForflexFunctionCallNode(function, this.parseParameterList());
                    }

                    if (this.functions.containsKey(token.value())) {
                        throw new ForflexTypeMismatchError(this.formula, token, ForflexAlgebra.class, ForflexFunction.class);
                    }

                    return new ForflexParameterNode(this.parameters, token.value());
                }
            }

            return null;
        }

        private List<Object> parseParameterList() throws ForflexUnexpectedTokenError, ForflexTypeMismatchError, ForflexUndeclaredIdentifierError {
            this.expect(ForflexTokenType.LPAREN);
            List<Object> params = new ArrayList<>();
            ForflexToken next = null;

            do {
                if (ForflexTokenType.STRING == this.peekToken(ForflexTokenType.STRING)) {
                    params.add(Objects.requireNonNull(this.nextTokenOfType(ForflexTokenType.STRING)).value());
                } else if (ForflexTokenType.RPAREN != this.peekToken(ForflexTokenType.RPAREN)) {
                    params.add(this.parseExpression());
                }

                next = this.expect(ForflexTokenType.COMMA, ForflexTokenType.RPAREN);
            } while (ForflexTokenType.COMMA == next.type());

            return params;
        }

        private ForflexTokenType peekToken(ForflexTokenType... types) {
            return this.tokens.get(this.index).type();
        }

        private ForflexToken nextTokenOfType(ForflexTokenType... types) {
            if (this.index >= this.tokens.size()) {
                return null;
            }

            ForflexToken next = this.tokens.get(this.index);

            if (Arrays.asList(types).contains(next.type())) {
                this.index++;
                return next;
            }

            return null;
        }

        private ForflexToken expect(ForflexTokenType... types) throws ForflexUnexpectedTokenError {
            ForflexToken ret = this.nextTokenOfType(types);

            if (null == ret) {
                throw new ForflexUnexpectedTokenError(this.formula, this.tokens.get(this.index));
            }

            return ret;
        }
    }
}
