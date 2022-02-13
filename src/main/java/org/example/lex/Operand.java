package org.example.lex;

import java.util.Set;

public interface Operand {
    <T extends Operand> Set<Class<T>> getAllowedNext();
}
