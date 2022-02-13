package org.example.parser;

import org.example.lex.LogicalOperator;
import org.example.lex.Operand;

import java.text.ParseException;
import java.util.Stack;

public class AbstractSyntaxTree {

    private final Operand rootNode;

    public AbstractSyntaxTree(String expression) throws ParseException {
        final Stack<Operand> postfixNotation = new PostfixNotationBuilder(expression).getPostfixNotation();
        rootNode = buildTree(postfixNotation);
    }

    private static Operand buildTree(Stack<Operand> postfixNotation) {
        Stack<Operand> control = new Stack<>();
        final Operand rootNode = postfixNotation.pop();
        control.push(rootNode);

        while (!postfixNotation.empty()) {
            final Operand parent = control.peek();

            if (parent instanceof LogicalOperator logicalOperator && logicalOperator.isCompleted()) {
                control.pop();
                continue;
            }

            final Operand curr = postfixNotation.pop();

            if (parent instanceof LogicalOperator logicalOperator) {
                logicalOperator.addOperand(curr);
                if (curr instanceof LogicalOperator) {
                    control.push(curr);
                }
            }
        }

        return rootNode;
    }

    public Operand getRootNode() {
        return rootNode;
    }

}
