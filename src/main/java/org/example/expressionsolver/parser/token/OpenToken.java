package org.example.expressionsolver.parser.token;

import org.example.expressionsolver.parser.operand.Operand;

import java.util.Set;

public class OpenToken implements Token {

    private static final Token INSTANCE = new OpenToken();

    private OpenToken() {
    }

    @Override
    public Set<Class<? extends Token>> getAllowedNext() {
        return Set.of(PredicateToken.class, NotToken.class, OpenToken.class);
    }

    @Override
    public Operand toOperand() {
        throw new UnsupportedOperationException("OpenToken can't be converted to Operand");
    }

    public static Token openToken() {
        return INSTANCE;
    }
}
