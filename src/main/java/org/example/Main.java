package org.example;

import org.example.expressionsolver.ExpressionSolver;

import java.text.ParseException;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.String.valueOf;

public class Main {
    public static void main(String[] args) throws ParseException {
        String expression = "!(expr1 || expr2) && expr3";

        Map<String, Supplier<Boolean>> suppliers = Map.of(
                "expr1", () -> false,
                "expr2", () -> false,
                "expr3", () -> true);

        ExpressionSolver expressionSolver = new ExpressionSolver(suppliers);
        boolean result = expressionSolver.solve(expression);

        for (var entry : suppliers.entrySet()) {
            expression = expression.replace(entry.getKey(), valueOf(entry.getValue().get()));
        }

        System.out.println(String.format("Expression '%s' gives '%s'", expression, result));
    }
}
