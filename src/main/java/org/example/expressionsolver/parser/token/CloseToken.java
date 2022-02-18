package org.example.expressionsolver.parser.token;

import org.example.expressionsolver.parser.operand.Operand;

import java.util.Set;

public class CloseToken implements Token {

    private static final Token INSTANCE = new CloseToken();

    private CloseToken() {
    }

    @Override
    public Set<Class<? extends Token>> getAllowedNext() {
        return Set.of(AndToken.class, OrToken.class, CloseToken.class);
    }

    @Override
    public Operand toOperand() {
        throw new UnsupportedOperationException("CloseToken can't be converted to Operand");
    }

    public static Token closeToken() {
        return INSTANCE;
    }
}
