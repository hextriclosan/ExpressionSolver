package org.example.expressionsolver.parser.token;

import org.example.expressionsolver.parser.operand.Operand;

import java.util.Set;

import static org.example.expressionsolver.parser.operand.NotOperator.notOperator;

public class NotToken implements Token {

    private static final Token INSTANCE = new NotToken();

    private NotToken() {
    }

    @Override
    public Set<Class<? extends Token>> getAllowedNext() {
        return Set.of(PredicateToken.class, OpenToken.class);
    }

    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public Operand toOperand() {
        return notOperator();
    }

    @Override
    public boolean isLeftToRight() {
        return false;
    }

    public static Token notToken() {
        return INSTANCE;
    }
}
