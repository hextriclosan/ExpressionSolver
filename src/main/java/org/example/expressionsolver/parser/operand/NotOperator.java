package org.example.expressionsolver.parser.operand;

public class NotOperator extends UnaryOperator {
    private NotOperator() {
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NotOperator;
    }

    public static NotOperator notOperator() {
        return new NotOperator();
    }
}
