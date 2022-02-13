package org.example.parser;

import org.example.lex.CloseOperator;
import org.example.lex.Predicate;
import org.example.lex.OpenOperator;
import org.example.lex.Operand;
import org.example.lex.Operator;

import java.text.ParseException;
import java.util.List;
import java.util.Stack;

public class PostfixNotationBuilder {

    private final Stack<Operand> postfixNotation;

    public PostfixNotationBuilder(String expression) throws ParseException {
        final List<Operand> operands = new Parser(expression).parse();
        this.postfixNotation = buildPostfixNotation(operands);
    }

    private static Stack<Operand> buildPostfixNotation(List<Operand> operands) {
        Stack<Operator> operators = new Stack<>();
        Stack<Operand> rpn = new Stack<>();

        for (Operand operand : operands) {

            if (operand instanceof Predicate) {
                rpn.push(operand);
            } else {
                final Operator operator = (Operator) operand;
                if (operator instanceof OpenOperator) {
                    operators.push(operator);
                    continue;
                }

                if (operator instanceof CloseOperator) {
                    while (!(operators.peek() instanceof OpenOperator)) {
                        rpn.push(operators.pop());
                    }
                    operators.pop();
                    continue;
                }

                while (!operators.empty() && operators.peek().getPrecedence() > operator.getPrecedence()) {
                    rpn.push(operators.pop());
                }
                operators.push(operator);
            }
        }

        rpn.addAll(operators);

        return rpn;
    }

    Stack<Operand> getPostfixNotation() {
        return postfixNotation;
    }

}
