package org.example.lex;

public interface LogicalOperator extends Operator{
    boolean isCompleted();

    void addOperand(Operand operand);
}
