package org.example.expressionsolver.parser.operand;

public class AndOperator extends BinaryOperator {
    private AndOperator() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AndOperator;
    }

    public static AndOperator andOperator() {
        return new AndOperator();
    }
}
