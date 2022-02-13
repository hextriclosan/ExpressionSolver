package org.example.lex;

public class AndOperator extends BinaryOperator {
    @Override
    public int getPrecedence() {
        return 2;
    }
}
