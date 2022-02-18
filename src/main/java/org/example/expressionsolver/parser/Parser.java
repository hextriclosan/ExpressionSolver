package org.example.expressionsolver.parser;

import org.example.expressionsolver.parser.token.CloseToken;
import org.example.expressionsolver.parser.token.OpenToken;
import org.example.expressionsolver.parser.token.Token;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.function.Predicate.not;
import static org.example.expressionsolver.parser.token.AndToken.andToken;
import static org.example.expressionsolver.parser.token.CloseToken.closeToken;
import static org.example.expressionsolver.parser.token.NotToken.notToken;
import static org.example.expressionsolver.parser.token.OpenToken.openToken;
import static org.example.expressionsolver.parser.token.OrToken.orToken;
import static org.example.expressionsolver.parser.token.PredicateToken.predicateToken;

public class Parser {

    public List<Token> parse(String expression) throws ParseException {

        expression = expression
                .replace("&&", " && ")
                .replace("||", " || ")
                .replace("!", " ! ")
                .replace("(", " ( ")
                .replace(")", " ) ");

        final List<String> postProcessedTokens = Arrays.stream(expression.split(" "))
                .filter(not(String::isEmpty))
                .toList();

        List<Token> tokens = new ArrayList<>();

        for (String token : postProcessedTokens) {
            switch (token) {
                case "&&":
                    tokens.add(andToken());
                    break;
                case "||":
                    tokens.add(orToken());
                    break;
                case "!":
                    tokens.add(notToken());
                    break;
                case "(":
                    tokens.add(openToken());
                    break;
                case ")":
                    tokens.add(closeToken());
                    break;
                default:
                    tokens.add(predicateToken(token));
            }
        }

        validate(tokens);

        return tokens;
    }

    private static void validate(List<Token> tokens) throws ParseException {

        Set<Class<? extends Token>> allowedNext = null;
        int parenthesesBalance = 0;
        for (int i = 0; i < tokens.size(); i++) {

            if (parenthesesBalance < 0) {
                throw new ParseException("parentheses error : extra ')'", 0); //fixme: track position
            }

            final Token token = tokens.get(i);

            if (i > 0 && !allowedNext.contains(token.getClass())) {
                throw new ParseException("invalid token: " + token, 0); //fixme: track position
            }

            if (token instanceof CloseToken) {
                --parenthesesBalance;
            } else if (token instanceof OpenToken) {
                ++parenthesesBalance;
            }

            allowedNext = token.getAllowedNext();
        }

        if (parenthesesBalance != 0) {
            throw new ParseException("parentheses error : extra '('", 0); //fixme: track position
        }
    }

}
