package org.example.expressionsolver.parser.operand;

import java.util.Objects;

public class PredicateOperand implements Operand {

    private final String name;

    private PredicateOperand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredicateOperand predicateOperand = (PredicateOperand) o;
        return Objects.equals(name, predicateOperand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static PredicateOperand predicateOperand(String name) {
        return new PredicateOperand(name);
    }

}
