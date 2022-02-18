package org.example.expressionsolver.parser;

import org.example.expressionsolver.parser.operand.Operand;
import org.example.expressionsolver.parser.operand.Operator;

import java.text.ParseException;
import java.util.Stack;

public class AbstractSyntaxTree {

    private final PostfixNotationBuilder postfixNotationBuilder;

    public AbstractSyntaxTree(PostfixNotationBuilder postfixNotationBuilder) {
        this.postfixNotationBuilder = postfixNotationBuilder;
    }

    private static Operand buildTree(Stack<Operand> postfixNotation) {
        Stack<Operand> control = new Stack<>();
        final Operand rootNode = postfixNotation.pop();
        control.push(rootNode);

        while (!postfixNotation.empty()) {
            final Operand parent = control.peek();

            if (parent instanceof Operator operator && operator.isCompleted()) {
                control.pop();
                continue;
            }

            final Operand curr = postfixNotation.pop();

            if (parent instanceof Operator operator) {
                operator.addChildOperand(curr);
                if (curr instanceof Operator) {
                    control.push(curr);
                }
            }
        }

        return rootNode;
    }

    public Operand getRootNode(String expression) throws ParseException {
        final Stack<Operand> postfixNotation = postfixNotationBuilder.getPostfixNotation(expression);
        return buildTree(postfixNotation);
    }

}
