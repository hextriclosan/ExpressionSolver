package org.example.expressionsolver.parser.operand;

public class OrOperator extends BinaryOperator {
    private OrOperator() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof OrOperator;
    }

    public static OrOperator orOperator() {
        return new OrOperator();
    }
}
