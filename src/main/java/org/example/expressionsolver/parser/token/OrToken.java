package org.example.expressionsolver.parser.token;

import org.example.expressionsolver.parser.operand.Operand;

import java.util.Set;

import static org.example.expressionsolver.parser.operand.OrOperator.orOperator;

public class OrToken implements Token {

    private static final Token INSTANCE = new OrToken();

    private OrToken() {
    }

    @Override
    public Set<Class<? extends Token>> getAllowedNext() {
        return Set.of(PredicateToken.class, NotToken.class, OpenToken.class);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public Operand toOperand() {
        return orOperator();
    }

    public static Token orToken() {
        return INSTANCE;
    }
}
