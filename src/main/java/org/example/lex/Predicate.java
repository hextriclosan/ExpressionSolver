package org.example.lex;

import java.util.Set;

public class Predicate implements Operand {

    private final String name;

    public Predicate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Set<Class<? extends Operand>> getAllowedNext() {
        return Set.of(AndOperator.class, OrOperator.class, CloseOperator.class);
    }
}
