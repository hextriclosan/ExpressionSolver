package org.example.expressionsolver.parser;

import org.example.expressionsolver.parser.operand.Operand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.List;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.expressionsolver.parser.operand.AndOperator.andOperator;
import static org.example.expressionsolver.parser.operand.NotOperator.notOperator;
import static org.example.expressionsolver.parser.operand.OrOperator.orOperator;
import static org.example.expressionsolver.parser.operand.PredicateOperand.predicateOperand;
import static org.example.expressionsolver.parser.token.AndToken.andToken;
import static org.example.expressionsolver.parser.token.CloseToken.closeToken;
import static org.example.expressionsolver.parser.token.NotToken.notToken;
import static org.example.expressionsolver.parser.token.OpenToken.openToken;
import static org.example.expressionsolver.parser.token.OrToken.orToken;
import static org.example.expressionsolver.parser.token.PredicateToken.predicateToken;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostfixNotationBuilderTest {

    private final static String DUMMY_EXPRESSION = "dummy";

    private PostfixNotationBuilder postfixNotationBuilder;

    @Mock
    private Parser parser;

    @BeforeEach
    void startUp() {
        postfixNotationBuilder = new PostfixNotationBuilder(parser);
    }

    @Test
    void simpleAnd() throws ParseException {
        when(parser.parse(eq(DUMMY_EXPRESSION))).thenReturn(List.of(predicateToken("e1"), andToken(), predicateToken("e2")));

        Stack<Operand> expected = new Stack<>();
        expected.push(predicateOperand("e1"));
        expected.push(predicateOperand("e2"));
        expected.push(andOperator());

        assertThat(postfixNotationBuilder.getPostfixNotation(DUMMY_EXPRESSION)).as("e1 && e2").isEqualTo(expected);
    }

    @Test
    void simpleAndandOr() throws ParseException {
        when(parser.parse(eq(DUMMY_EXPRESSION))).thenReturn(List.of(predicateToken("e1"), andToken(), predicateToken("e2"), orToken(), predicateToken("e3")));

        Stack<Operand> expected = new Stack<>();
        expected.push(predicateOperand("e1"));
        expected.push(predicateOperand("e2"));
        expected.push(andOperator());
        expected.push(predicateOperand("e3"));
        expected.push(orOperator());

        assertThat(postfixNotationBuilder.getPostfixNotation(DUMMY_EXPRESSION)).as("e1 && e2 || e3").isEqualTo(expected);
    }

    @Test
    void simpleAndandOrGrouped() throws ParseException {
        when(parser.parse(eq(DUMMY_EXPRESSION))).thenReturn(List.of(predicateToken("e1"), andToken(), openToken(), predicateToken("e2"), orToken(), predicateToken("e3"), closeToken()));

        Stack<Operand> expected = new Stack<>();
        expected.push(predicateOperand("e1"));
        expected.push(predicateOperand("e2"));
        expected.push(predicateOperand("e3"));
        expected.push(orOperator());
        expected.push(andOperator());

        assertThat(postfixNotationBuilder.getPostfixNotation(DUMMY_EXPRESSION)).as("e1 && (e2 || e3)").isEqualTo(expected);
    }

    @Test
    void simpleNot() throws ParseException {
        when(parser.parse(eq(DUMMY_EXPRESSION))).thenReturn(List.of(predicateToken("e1"), andToken(), notToken(), predicateToken("e2")));

        Stack<Operand> expected = new Stack<>();
        expected.push(predicateOperand("e1"));
        expected.push(predicateOperand("e2"));
        expected.push(notOperator());
        expected.push(andOperator());

        assertThat(postfixNotationBuilder.getPostfixNotation(DUMMY_EXPRESSION)).as("e1 && !e2").isEqualTo(expected);
    }

    @Test
    void complexExpression() throws ParseException {
        when(parser.parse(eq(DUMMY_EXPRESSION))).thenReturn(List.of(notToken(), openToken(), predicateToken("e1"), andToken(), predicateToken("e2"), closeToken(), orToken(), notToken(), openToken(), predicateToken("e3"), andToken(), predicateToken("e4"), closeToken()));

        Stack<Operand> expected = new Stack<>();
        expected.push(predicateOperand("e1"));
        expected.push(predicateOperand("e2"));
        expected.push(andOperator());
        expected.push(notOperator());
        expected.push(predicateOperand("e3"));
        expected.push(predicateOperand("e4"));
        expected.push(andOperator());
        expected.push(notOperator());
        expected.push(orOperator());

        assertThat(postfixNotationBuilder.getPostfixNotation(DUMMY_EXPRESSION)).as("!(e1 && e2) || !(e3 && e4)").isEqualTo(expected);
    }
}