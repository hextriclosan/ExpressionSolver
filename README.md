# ExpressionSolver

This is a simple solver of logical expressions.

Supported operators:
 - logical `||`, `&&`, `!`
 - grouping `(`, `)`

### Example

```java
import org.example.ExpressionSolver;

import java.text.ParseException;
import java.util.Map;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws ParseException {
        Map<String, Supplier<Boolean>> suppliers = Map.of(
                "expr1", () -> false,
                "expr2", () -> false,
                "expr3", () -> true);

        ExpressionSolver expressionSolver = new ExpressionSolver(suppliers);
        boolean result = expressionSolver.solve("!(expr1 || expr2) && expr3");
        System.out.println(result);
    }
}
```