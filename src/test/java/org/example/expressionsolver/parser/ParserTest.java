package org.example.expressionsolver.parser;

import org.example.expressionsolver.parser.token.Token;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.expressionsolver.parser.token.AndToken.andToken;
import static org.example.expressionsolver.parser.token.PredicateToken.predicateToken;

class ParserTest {

    @Test
    void simpleAnd() throws ParseException {
        Parser parser = new Parser();
        final List<Token> parsed = parser.parse("expr1 && expr2");

        assertThat(parsed).isEqualTo(List.of(
                predicateToken("expr1"),
                andToken(),
                predicateToken("expr2")));
    }

}