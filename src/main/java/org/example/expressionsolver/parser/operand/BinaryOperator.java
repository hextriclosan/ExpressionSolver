package org.example.expressionsolver.parser.operand;

abstract class BinaryOperator extends LogicalOperator {

    private static final int CHILDREN_NUMBER = 2;

    @Override
    protected int getChildrenNumber() {
        return CHILDREN_NUMBER;
    }

}
