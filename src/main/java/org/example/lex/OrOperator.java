package org.example.lex;

public class OrOperator extends BinaryOperator {
    @Override
    public int getPrecedence() {
        return 1;
    }
}
