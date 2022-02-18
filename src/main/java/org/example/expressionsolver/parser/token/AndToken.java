package org.example.expressionsolver.parser.token;

import org.example.expressionsolver.parser.operand.Operand;

import java.util.Set;

import static org.example.expressionsolver.parser.operand.AndOperator.andOperator;

public class AndToken implements Token {

    private static final Token INSTANCE = new AndToken();

    private AndToken() {
    }

    @Override
    public Set<Class<? extends Token>> getAllowedNext() {
        return Set.of(PredicateToken.class, NotToken.class, OpenToken.class);
    }

    @Override
    public int getPrecedence() {
        return 2;
    }

    @Override
    public Operand toOperand() {
        return andOperator();
    }

    public static Token andToken() {
        return INSTANCE;
    }
}
