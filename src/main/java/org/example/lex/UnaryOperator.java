package org.example.lex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class UnaryOperator implements LogicalOperator { //todo: consider remove public

    final private List<Operand> operands = new ArrayList<>();

    @Override
    public Set<Class<? extends Operand>> getAllowedNext() {
        return Set.of(Predicate.class, OpenOperator.class);
    }

    @Override
    public void addOperand(Operand operand) {
        if (operands.size() == 1) {
            throw new UnsupportedOperationException();
        }
        operands.add(operand);
    }

    @Override
    public boolean isCompleted() {
        return operands.size() == 1;
    }

    public Operand getChild() {
        return operands.get(0);
    }

}
