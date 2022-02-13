package org.example.lex;


public class NotOperator extends UnaryOperator {
    @Override
    public int getPrecedence() {
        return 3;
    }
}
