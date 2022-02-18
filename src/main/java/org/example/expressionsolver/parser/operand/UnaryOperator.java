package org.example.expressionsolver.parser.operand;

abstract class UnaryOperator extends LogicalOperator {

    private static final int CHILDREN_NUMBER = 1;

    @Override
    protected int getChildrenNumber() {
        return CHILDREN_NUMBER;
    }

}
