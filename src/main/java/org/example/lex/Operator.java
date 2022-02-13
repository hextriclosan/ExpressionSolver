package org.example.lex;

public interface Operator extends Operand {
    int getPrecedence();
}
