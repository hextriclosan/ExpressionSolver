package org.example.expressionsolver.parser;

import org.example.expressionsolver.parser.operand.AndOperator;
import org.example.expressionsolver.parser.operand.Operand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.expressionsolver.parser.operand.AndOperator.andOperator;
import static org.example.expressionsolver.parser.operand.PredicateOperand.predicateOperand;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractSyntaxTreeTest {

    private static final String DUMMY_EXPRESSION = "dummy";

    @Mock
    private PostfixNotationBuilder postfixNotationBuilder;

    @Test
    void simpleAbstractSyntaxTree() throws ParseException {
        Stack<Operand> postfixNotation = new Stack<>();
        postfixNotation.push(predicateOperand("expr1"));
        postfixNotation.push(predicateOperand("expr2"));
        postfixNotation.push(andOperator());
        when(postfixNotationBuilder.getPostfixNotation(eq(DUMMY_EXPRESSION))).thenReturn(postfixNotation);

        AndOperator rootNodeExpected = andOperator();
        rootNodeExpected.addChildOperand(predicateOperand("expr2"));
        rootNodeExpected.addChildOperand(predicateOperand("expr1"));

        final AbstractSyntaxTree syntaxTree = new AbstractSyntaxTree(postfixNotationBuilder);
        final AndOperator rootNode = (AndOperator)syntaxTree.getRootNode(DUMMY_EXPRESSION);
        assertThat(rootNode).isEqualTo(rootNodeExpected);
        assertThat(rootNode.popChild()).isEqualTo(rootNodeExpected.popChild());
        assertThat(rootNode.popChild()).isEqualTo(rootNodeExpected.popChild());
    }

}