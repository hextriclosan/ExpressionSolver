package org.example;

import org.example.expressionsolver.ExpressionSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpressionSolverTest {

    @Mock
    private Predicates predicates;

    private Map<String, Supplier<Boolean>> suppliers;
    private ExpressionSolver expressionSolver;

    @BeforeEach
    void startUp() {
        suppliers = Map.of(
                "e1", () -> predicates.fn1(),
                "e2", () -> predicates.fn2(),
                "e3", () -> predicates.fn3(),
                "e4", () -> predicates.fn4()
        );

        expressionSolver = new ExpressionSolver(suppliers);
    }

    @Test
    void predicateOnly() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        
        assertThat(expressionSolver.solve("e1")).isEqualTo(true);

        verify(predicates, times(1)).fn1();
    }

    @Test
    void andsWithoutParenthesesFirstIsFalse() throws ParseException {
        when(predicates.fn1()).thenReturn(false);

        assertThat(expressionSolver.solve("e1 && e2 && e3 && e4")).isEqualTo(false);

        verify(predicates, times(1)).fn1();
        verify(predicates, never()).fn2();
        verify(predicates, never()).fn3();
        verify(predicates, never()).fn4();
    }

    @Test
    void andsWithoutParenthesesLastIsFalse() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        when(predicates.fn2()).thenReturn(true);
        when(predicates.fn3()).thenReturn(true);
        when(predicates.fn4()).thenReturn(false);

        assertThat(expressionSolver.solve("e1 && e2 && e3 && e4")).isEqualTo(false);

        verify(predicates, times(1)).fn1();
        verify(predicates, times(1)).fn2();
        verify(predicates, times(1)).fn3();
        verify(predicates, times(1)).fn4();
    }

    @Test
    void andsWithoutParenthesesAllAreTrue() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        when(predicates.fn2()).thenReturn(true);
        when(predicates.fn3()).thenReturn(true);
        when(predicates.fn4()).thenReturn(true);

        assertThat(expressionSolver.solve("e1 && e2 && e3 && e4")).isEqualTo(true);

        verify(predicates, times(1)).fn1();
        verify(predicates, times(1)).fn2();
        verify(predicates, times(1)).fn3();
        verify(predicates, times(1)).fn4();
    }

    @Test
    void andsWithParenthesesReverseAllAreTrue() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        when(predicates.fn2()).thenReturn(true);
        when(predicates.fn3()).thenReturn(true);
        when(predicates.fn4()).thenReturn(true);

        assertThat(expressionSolver.solve("((e1 && e2) && e3) && e4")).isEqualTo(true);

        verify(predicates, times(1)).fn1();
        verify(predicates, times(1)).fn2();
        verify(predicates, times(1)).fn3();
        verify(predicates, times(1)).fn4();
    }

    @Test
    void andsWithParenthesesReverseLastIsFalse() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        when(predicates.fn2()).thenReturn(true);
        when(predicates.fn3()).thenReturn(true);
        when(predicates.fn4()).thenReturn(false);

        assertThat(expressionSolver.solve("((e1 && e2) && e3) && e4")).isEqualTo(false);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, times(1)).fn2();
        orderVerifier.verify(predicates, times(1)).fn3();
        orderVerifier.verify(predicates, times(1)).fn4();
    }

    @Test
    void andsWithParenthesesInMiddleAllAreTrue() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        when(predicates.fn2()).thenReturn(true);
        when(predicates.fn3()).thenReturn(true);
        when(predicates.fn4()).thenReturn(true);

        assertThat(expressionSolver.solve("e1 && (e2 && e3) && e4")).isEqualTo(true);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, times(1)).fn2();
        orderVerifier.verify(predicates, times(1)).fn3();
        orderVerifier.verify(predicates, times(1)).fn4();
    }

    @Test
    void orsAllAreTrue() throws ParseException {
        when(predicates.fn1()).thenReturn(true);

        assertThat(expressionSolver.solve("e1 || e2 || e3")).isEqualTo(true);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, never()).fn2();
        orderVerifier.verify(predicates, never()).fn3();
    }

    @Test
    void orsSecondIsTrue() throws ParseException {
        when(predicates.fn1()).thenReturn(false);
        when(predicates.fn2()).thenReturn(true);

        assertThat(expressionSolver.solve("e1 || e2 || e3")).isEqualTo(true);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, times(1)).fn2();
        orderVerifier.verify(predicates, never()).fn3();
    }

    @Test
    void andsAndOr() throws ParseException {
        when(predicates.fn1()).thenReturn(false);
        when(predicates.fn3()).thenReturn(true);

        assertThat(expressionSolver.solve("e1 && e2 || e3")).isEqualTo(true);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, times(1)).fn3();
    }

    @Test
    void andsAndOrWithParentheses() throws ParseException {
        when(predicates.fn1()).thenReturn(false);
        when(predicates.fn2()).thenReturn(false);

        assertThat(expressionSolver.solve("(e1 || e2) && e3")).isEqualTo(false);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, times(1)).fn2();
        orderVerifier.verify(predicates, never()).fn3();
    }

    @Test
    void not() throws ParseException {
        when(predicates.fn1()).thenReturn(true);

        assertThat(expressionSolver.solve("!e1")).isEqualTo(false);

        verify(predicates, times(1)).fn1();
    }

    @Test
    void notComplex() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        when(predicates.fn2()).thenReturn(true);
        when(predicates.fn3()).thenReturn(false);

        assertThat(expressionSolver.solve("!(e1 && e2) || !(e3 && e4)")).isEqualTo(true);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, times(1)).fn2();
        orderVerifier.verify(predicates, times(1)).fn3();
        orderVerifier.verify(predicates, never()).fn4();
    }

    @Test
    void andNot() throws ParseException {
        when(predicates.fn1()).thenReturn(true);
        when(predicates.fn2()).thenReturn(false);

        assertThat(expressionSolver.solve("e1 && !e2")).isEqualTo(true);

        InOrder orderVerifier = inOrder(predicates);
        orderVerifier.verify(predicates, times(1)).fn1();
        orderVerifier.verify(predicates, times(1)).fn2();
    }

    @Test
    void invalidExpression1() {
        assertThatThrownBy(() -> expressionSolver.solve("e1 e2")).isInstanceOf(ParseException.class);
    }

    @Test
    void invalidExpression2() {
        assertThatThrownBy(() -> expressionSolver.solve("e1 && && e2")).isInstanceOf(ParseException.class);
    }

    @Test
    void invalidExpression3() {
        assertThatThrownBy(() -> expressionSolver.solve("e1 ! && e2")).isInstanceOf(ParseException.class);
    }

    @Test
    void parentheses1() {
        assertThatThrownBy(() -> expressionSolver.solve("(e1 && e2")).isInstanceOf(ParseException.class);
    }

    @Test
    void parentheses2() {
        assertThatThrownBy(() -> expressionSolver.solve("e1 && e2)")).isInstanceOf(ParseException.class);
    }

    @Test
    void parentheses3() {
        assertThatThrownBy(() -> expressionSolver.solve("e1) && e2 && (e3")).isInstanceOf(ParseException.class);
    }

    private interface Predicates {
        boolean fn1();
        boolean fn2();
        boolean fn3();
        boolean fn4();
    }
}