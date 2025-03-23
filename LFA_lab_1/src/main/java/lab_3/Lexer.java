package lab_3;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    private char peek() {
        return position < input.length() ? input.charAt(position) : '\0';
    }

    private char advance() {
        return input.charAt(position++);
    }

    private boolean match(String word) {
        return input.startsWith(word, position);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            char current = peek();

            if (Character.isWhitespace(current)) {
                advance(); // Ignore whitespace
            } else if (match("true")) {
                tokens.add(new Token(TokenType.TRUE, "true"));
                position += 4;
            } else if (match("false")) {
                tokens.add(new Token(TokenType.FALSE, "false"));
                position += 5;
            } else if (match("AND")) {
                tokens.add(new Token(TokenType.AND, "AND"));
                position += 3;
            } else if (match("OR")) {
                tokens.add(new Token(TokenType.OR, "OR"));
                position += 2;
            } else if (match("NOT")) {
                tokens.add(new Token(TokenType.NOT, "NOT"));
                position += 3;
            } else if (match("NAND")) {
                tokens.add(new Token(TokenType.NAND, "NAND"));
                position += 4;
            } else if (match("NOR")) {
                tokens.add(new Token(TokenType.NOR, "NOR"));
                position += 3;
            } else if (match("XOR")) {
                tokens.add(new Token(TokenType.XOR, "XOR"));
                position += 3;
            } else if (match("XNOR")) {
                tokens.add(new Token(TokenType.XNOR, "XNOR"));
                position += 4;
            } else if (current == '(') {
                tokens.add(new Token(TokenType.LPAREN, "("));
                advance();
            } else if (current == ')') {
                tokens.add(new Token(TokenType.RPAREN, ")"));
                advance();
            } else if (Character.isDigit(current) ) {
                if (current == '0' || current == '1') {
                    tokens.add(new Token(TokenType.INTEGER, String.valueOf(current)));
                    advance();
                } else {
                    throw new RuntimeException("Invalid integer: " + current);
                }
            } else if (Character.isLetter(current)) {
                StringBuilder varName = new StringBuilder();
                while (Character.isLetterOrDigit(peek()) || peek() == '_') {
                    varName.append(advance());
                }
                tokens.add(new Token(TokenType.VARIABLE, varName.toString()));
            } else {
                throw new RuntimeException("Unexpected character: " + current);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}
