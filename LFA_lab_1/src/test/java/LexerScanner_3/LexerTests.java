package LexerScanner_3;

import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

public class LexerTests {
    @Test
    public void testBooleanLiterals() {
        Lexer lexer = new Lexer("true false");
        List<Token> tokens = lexer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.TRUE, tokens.get(0).type);
        assertEquals("true", tokens.get(0).lexeme);
        assertEquals(TokenType.FALSE, tokens.get(1).type);
        assertEquals("false", tokens.get(1).lexeme);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    public void testOperators() {
        Lexer lexer = new Lexer("AND OR NOT NAND NOR XOR XNOR");
        List<Token> tokens = lexer.tokenize();

        assertEquals(8, tokens.size());  // 7 tokens + EOF
        assertEquals(TokenType.AND, tokens.get(0).type);
        assertEquals("AND", tokens.get(0).lexeme);

        assertEquals(TokenType.OR, tokens.get(1).type);
        assertEquals("OR", tokens.get(1).lexeme);

        assertEquals(TokenType.NOT, tokens.get(2).type);
        assertEquals("NOT", tokens.get(2).lexeme);

        assertEquals(TokenType.NAND, tokens.get(3).type);
        assertEquals("NAND", tokens.get(3).lexeme);

        assertEquals(TokenType.NOR, tokens.get(4).type);
        assertEquals("NOR", tokens.get(4).lexeme);

        assertEquals(TokenType.XOR, tokens.get(5).type);
        assertEquals("XOR", tokens.get(5).lexeme);

        assertEquals(TokenType.XNOR, tokens.get(6).type);
        assertEquals("XNOR", tokens.get(6).lexeme);

        assertEquals(TokenType.EOF, tokens.get(7).type);
    }


    @Test
    public void testParentheses() {
        Lexer lexer = new Lexer("( )");
        List<Token> tokens = lexer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.LPAREN, tokens.get(0).type);
        assertEquals("(", tokens.get(0).lexeme);
        assertEquals(TokenType.RPAREN, tokens.get(1).type);
        assertEquals(")", tokens.get(1).lexeme);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    public void testIntegerValues() {
        Lexer lexer = new Lexer("0 1");
        List<Token> tokens = lexer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.INTEGER, tokens.get(0).type);
        assertEquals("0", tokens.get(0).lexeme);
        assertEquals(TokenType.INTEGER, tokens.get(1).type);
        assertEquals("1", tokens.get(1).lexeme);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    public void testVariableNames() {
        Lexer lexer = new Lexer("x Y var_name var0_");
        List<Token> tokens = lexer.tokenize();

        assertEquals(5, tokens.size());
        assertEquals(TokenType.VARIABLE, tokens.get(0).type);
        assertEquals("x", tokens.get(0).lexeme);
        assertEquals(TokenType.VARIABLE, tokens.get(1).type);
        assertEquals("Y", tokens.get(1).lexeme);
        assertEquals(TokenType.VARIABLE, tokens.get(2).type);
        assertEquals("var_name", tokens.get(2).lexeme);
        assertEquals(TokenType.VARIABLE, tokens.get(3).type);
        assertEquals("var0_", tokens.get(3).lexeme);
        assertEquals(TokenType.EOF, tokens.get(4).type);
    }

    @Test
    public void testIllegalVariableNameUnderscore() {
        Lexer lexer = new Lexer("_underscore");

        Exception exception = assertThrows(RuntimeException.class, lexer::tokenize);
        assertTrue(exception.getMessage().contains("Unexpected character: _"));
    }

    @Test
    public void testComplexExpression() {
        Lexer lexer = new Lexer("A AND (B OR C) NOT true");
        List<Token> tokens = lexer.tokenize();

        assertEquals(10, tokens.size());
        assertEquals(TokenType.VARIABLE, tokens.get(0).type);
        assertEquals("A", tokens.get(0).lexeme);
        assertEquals(TokenType.AND, tokens.get(1).type);
        assertEquals("AND", tokens.get(1).lexeme);
        assertEquals(TokenType.LPAREN, tokens.get(2).type);
        assertEquals("(", tokens.get(2).lexeme);
        assertEquals(TokenType.VARIABLE, tokens.get(3).type);
        assertEquals("B", tokens.get(3).lexeme);
        assertEquals(TokenType.OR, tokens.get(4).type);
        assertEquals("OR", tokens.get(4).lexeme);
        assertEquals(TokenType.VARIABLE, tokens.get(5).type);
        assertEquals("C", tokens.get(5).lexeme);
        assertEquals(TokenType.RPAREN, tokens.get(6).type);
        assertEquals(")", tokens.get(6).lexeme);
        assertEquals(TokenType.NOT, tokens.get(7).type);
        assertEquals("NOT", tokens.get(7).lexeme);
        assertEquals(TokenType.TRUE, tokens.get(8).type);
        assertEquals("true", tokens.get(8).lexeme);
        assertEquals(TokenType.EOF, tokens.get(9).type);
    }

    @Test
    public void testUnexpectedCharacter() {
        Lexer lexer = new Lexer("@");

        Exception exception = assertThrows(RuntimeException.class, lexer::tokenize);
        assertTrue(exception.getMessage().contains("Unexpected character: @"));
    }

    @Test
    public void testInvalidInteger() {
        Lexer lexer = new Lexer("2");

        Exception exception = assertThrows(RuntimeException.class, lexer::tokenize);
        assertTrue(exception.getMessage().contains("Invalid integer: 2"));
    }
}
