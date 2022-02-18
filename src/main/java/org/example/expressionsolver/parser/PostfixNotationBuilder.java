package org.example.expressionsolver.parser;

import org.example.expressionsolver.parser.operand.Operand;
import org.example.expressionsolver.parser.token.CloseToken;
import org.example.expressionsolver.parser.token.OpenToken;
import org.example.expressionsolver.parser.token.PredicateToken;
import org.example.expressionsolver.parser.token.Token;

import java.text.ParseException;
import java.util.List;
import java.util.Stack;

public class PostfixNotationBuilder {

    private final Parser parser;

    public PostfixNotationBuilder(Parser parser) {
        this.parser = parser;
    }

    public Stack<Operand> getPostfixNotation(String expression) throws ParseException {
        final List<Token> tokens = parser.parse(expression);
        return buildPostfixNotation(tokens);
    }

    private static Stack<Operand> buildPostfixNotation(List<Token> tokens) {
        Stack<Token> operators = new Stack<>();
        Stack<Operand> postfixNotation = new Stack<>();

        for (Token token : tokens) {

            switch (token) {
                case PredicateToken predicateToken:
                    postfixNotation.push(predicateToken.toOperand());
                    break;
                case OpenToken openToken:
                    operators.push(openToken);
                    continue;
                case CloseToken ignored:
                    while (!(operators.peek() instanceof OpenToken)) {
                        postfixNotation.push(operators.pop().toOperand());
                    }
                    operators.pop();
                    continue;
                default:
                    while (!operators.empty() && operators.peek().getPrecedence() > token.getPrecedence()) {
                        postfixNotation.push(operators.pop().toOperand());
                    }

                    if (token.isLeftToRight()) {
                        operators.push(token);
                    } else {
                        int pos = operators.size() > 0 ? operators.size() - 1 : 0;
                        operators.add(pos, token);
                    }
            }

        }

        operators.forEach(token -> postfixNotation.push(token.toOperand()));

        return postfixNotation;
    }

}
