package org.example.parser;

import org.example.lex.AndOperator;
import org.example.lex.CloseOperator;
import org.example.lex.Predicate;
import org.example.lex.NotOperator;
import org.example.lex.OpenOperator;
import org.example.lex.Operand;
import org.example.lex.OrOperator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.function.Predicate.not;

public class Parser {

    private final String expression;

    public Parser(String expression) {
        this.expression = expression.replace("&&", " && ")
                .replace("||", " || ")
                .replace("!", " ! ")
                .replace("(", " ( ")
                .replace(")", " ) ");
    }

    public List<Operand> parse() throws ParseException {

        final List<String> tokens = Arrays.stream(expression.split(" "))
                .filter(not(String::isEmpty))
                .toList();

        List<Operand> operands = new ArrayList<>();

        for (String token : tokens) {
            switch (token) {
                case "&&":
                    operands.add(new AndOperator());
                    break;
                case "||":
                    operands.add(new OrOperator());
                    break;
                case "!":
                    operands.add(new NotOperator());
                    break;
                case "(":
                    operands.add(new OpenOperator());
                    break;
                case ")":
                    operands.add(new CloseOperator());
                    break;
                default:
                    operands.add(new Predicate(token));
            }
        }

        validate(operands);

        return operands;
    }

    private static void validate(List<Operand> operands) throws ParseException {

        Set<Class<Operand>> allowedNext = null;
        int parenthesesBalance = 0;
        for (int i = 0; i < operands.size(); i++) {

            if (parenthesesBalance < 0) {
                throw new ParseException("parentheses error : extra ')'", 0); //fixme: track position
            }

            final Operand operand = operands.get(i);

            if (i > 0 && !allowedNext.contains(operand.getClass())) {
                throw new ParseException("invalid operand: " + operand, 0); //fixme: track position
            }

            if (operand instanceof CloseOperator) {
                --parenthesesBalance;
            } else if (operand instanceof OpenOperator) {
                ++parenthesesBalance;
            }

            allowedNext = operand.getAllowedNext();
        }

        if (parenthesesBalance != 0) {
            throw new ParseException("parentheses error : extra '('", 0); //fixme: track position
        }
    }

}
