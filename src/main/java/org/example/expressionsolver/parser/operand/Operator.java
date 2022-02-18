package org.example.expressionsolver.parser.operand;

public interface Operator extends Operand {

    boolean isCompleted();

    void addChildOperand(Operand operand);

}
