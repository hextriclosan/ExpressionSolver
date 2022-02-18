package org.example.expressionsolver.parser.token;

import org.example.expressionsolver.parser.operand.Operand;

import java.util.Set;

public interface Token {
    Set<Class<? extends Token>> getAllowedNext();

    Operand toOperand();

    default int getPrecedence() {
        return 0;
    }

    default boolean isLeftToRight() {
        return true;
    }
}
