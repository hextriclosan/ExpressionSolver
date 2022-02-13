package org.example;

import org.example.lex.AndOperator;
import org.example.lex.OrOperator;
import org.example.lex.Predicate;
import org.example.lex.NotOperator;
import org.example.lex.Operand;
import org.example.parser.AbstractSyntaxTree;

import java.text.ParseException;
import java.util.Map;
import java.util.function.Supplier;

public class ExpressionSolver {

    private final Map<String, Supplier<Boolean>> suppliers;

    public ExpressionSolver(Map<String, Supplier<Boolean>> suppliers) {
        this.suppliers = suppliers;
    }

    public boolean solve(String expression) throws ParseException {
        final AbstractSyntaxTree abstractSyntaxTree = new AbstractSyntaxTree(expression);
        final Operand rootNode = abstractSyntaxTree.getRootNode();

        return solve(rootNode);
    }

    private boolean solve(Operand operand) {
        return switch (operand) {
            case AndOperator andOperator -> solve(andOperator.getLeft()) && solve(andOperator.getRight());
            case OrOperator orOperator -> solve(orOperator.getLeft()) || solve(orOperator.getRight());
            case NotOperator notOperator -> !solve(notOperator.getChild());
            case Predicate predicate -> suppliers.get(predicate.getName()).get();
            default -> throw new UnsupportedOperationException("unsupported operand: " + operand);
        };
    }
}
