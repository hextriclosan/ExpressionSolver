package org.example.expressionsolver.parser.operand;

import java.util.Stack;

abstract class LogicalOperator implements Operator {

    private final Stack<Operand> operands = new Stack<>();

    @Override
    public boolean isCompleted() {
        return operands.size() == getChildrenNumber();
    }

    @Override
    public void addChildOperand(Operand operand) {
        if (operands.size() == getChildrenNumber()) {
            throw new UnsupportedOperationException();
        }
        operands.push(operand);
    }

    public boolean hasChildren() {
        return !operands.empty();
    }

    public Operand popChild() {
        return operands.pop();
    }

    protected abstract int getChildrenNumber();
}
