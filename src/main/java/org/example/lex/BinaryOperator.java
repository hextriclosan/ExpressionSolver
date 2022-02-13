package org.example.lex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BinaryOperator implements LogicalOperator { //todo: consider remove public

    private final List<Operand> operands = new ArrayList<>();

    @Override
    public Set<Class<? extends Operand>> getAllowedNext() {
        return Set.of(Predicate.class, NotOperator.class, OpenOperator.class);
    }

    public Operand getLeft() {
        return operands.get(0);
    }

    public Operand getRight() {
        return operands.get(1);
    }

    @Override
    public void addOperand(Operand operand) {
        if (operands.size() == 2) {
            throw new UnsupportedOperationException();
        }
        operands.add(0, operand);
    }

    @Override
    public boolean isCompleted() {
        return operands.size() == 2;
    }

}
