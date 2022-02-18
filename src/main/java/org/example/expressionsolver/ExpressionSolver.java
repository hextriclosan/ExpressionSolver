package org.example.expressionsolver;

import org.example.expressionsolver.parser.AbstractSyntaxTree;
import org.example.expressionsolver.parser.Parser;
import org.example.expressionsolver.parser.PostfixNotationBuilder;
import org.example.expressionsolver.parser.operand.AndOperator;
import org.example.expressionsolver.parser.operand.NotOperator;
import org.example.expressionsolver.parser.operand.Operand;
import org.example.expressionsolver.parser.operand.OrOperator;
import org.example.expressionsolver.parser.operand.PredicateOperand;

import java.text.ParseException;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

public class ExpressionSolver {

    private final Map<String, Supplier<Boolean>> suppliers;

    public ExpressionSolver(Map<String, Supplier<Boolean>> suppliers) {
        this.suppliers = suppliers;
    }

    public boolean solve(String expression) throws ParseException {
        final Parser parser = new Parser();
        final PostfixNotationBuilder postfixNotationBuilder = new PostfixNotationBuilder(parser);
        final AbstractSyntaxTree abstractSyntaxTree = new AbstractSyntaxTree(postfixNotationBuilder);
        final Operand rootNode = abstractSyntaxTree.getRootNode(expression);

        Boolean result = null;
        Stack<Operand> operandStack = new Stack<>();
        operandStack.push(rootNode);
        while (!operandStack.empty()) {
            Operand operand = operandStack.peek();
            switch (operand) {
                case AndOperator andOperator:
                    if (andOperator.hasChildren()) {
                        final Operand andChild = andOperator.popChild();
                        if (andOperator.hasChildren() /* left */ || result /* right */) {
                            operandStack.push(andChild);
                            continue;
                        }
                    }
                    break;
                case OrOperator orOperator:
                    if (orOperator.hasChildren()) {
                        final Operand orChild = orOperator.popChild();
                        if (orOperator.hasChildren() /* left */ || !result /* right */) {
                            operandStack.push(orChild);
                            continue;
                        }
                    }
                    break;
                case NotOperator notOperator:
                    if (notOperator.hasChildren()) {
                        operandStack.push(notOperator.popChild());
                        continue;
                    }
                    result = !result;
                    break;
                case PredicateOperand predicateOperand:
                    result = suppliers.get(predicateOperand.getName()).get();
                    break;
                default:
                    throw new UnsupportedOperationException("unsupported operand: " + operand);
            }
            operandStack.pop();
        }

        return result;
    }
}
