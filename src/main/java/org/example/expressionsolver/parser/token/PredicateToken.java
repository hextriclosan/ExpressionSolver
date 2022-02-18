package org.example.expressionsolver.parser.token;

import org.example.expressionsolver.parser.operand.Operand;

import java.util.Objects;
import java.util.Set;

import static org.example.expressionsolver.parser.operand.PredicateOperand.predicateOperand;

public class PredicateToken implements Token {

    private final String name;

    private PredicateToken(String name) {
        this.name = name;
    }

    @Override
    public Set<Class<? extends Token>> getAllowedNext() {
        return Set.of(AndToken.class, OrToken.class, CloseToken.class);
    }

    @Override
    public Operand toOperand() {
        return predicateOperand(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredicateToken that = (PredicateToken) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static Token predicateToken(String name) {
        return new PredicateToken(name);
    }
}
