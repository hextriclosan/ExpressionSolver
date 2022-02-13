package org.example.lex;

import java.util.Set;

public class OpenOperator implements Operator {
    @Override
    public Set<Class<? extends Operand>> getAllowedNext() {
        return Set.of(Predicate.class, NotOperator.class, OpenOperator.class);
    }

    @Override
    public int getPrecedence() {
        return 0;
    }
}
