package org.example.lex;

import java.util.Set;

public class CloseOperator implements Operator {
    @Override
    public Set<Class<? extends Operand>> getAllowedNext() {
        return Set.of(AndOperator.class, OrOperator.class, CloseOperator.class);
    }

    @Override
    public int getPrecedence() {
        return 0;
    }
}
