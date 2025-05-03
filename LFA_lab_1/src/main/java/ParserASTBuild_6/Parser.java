package ParserASTBuild_6;

import LexerScanner_3.Token;
import LexerScanner_3.TokenType;
import ParserASTBuild_6.AST.*;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ASTNode parse() {
        ASTNode expr = expression();

        // Make sure we've consumed all tokens except EOF
        if (peek().type != TokenType.EOF) {
            throw new RuntimeException("Unexpected token: " + peek().lexeme);
        }

        return expr;
    }

    // Grammar:
    // expression -> term (("XOR" | "XNOR") term)*
    // term -> factor (("OR" | "NOR") factor)*
    // factor -> unary (("AND" | "NAND") unary)*
    // unary -> "NOT" unary | primary
    // primary -> "true" | "false" | INTEGER | VARIABLE | "(" expression ")"

    private ASTNode expression() {
        ASTNode expr = term();

        while (match(TokenType.XOR) || match(TokenType.XNOR)) {
            Token operator = previous();
            ASTNode right = term();
            expr = new BinaryOpNode(operator.type, expr, right);
        }

        return expr;
    }

    private ASTNode term() {
        ASTNode expr = factor();

        while (match(TokenType.OR) || match(TokenType.NOR)) {
            Token operator = previous();
            ASTNode right = factor();
            expr = new BinaryOpNode(operator.type, expr, right);
        }

        return expr;
    }

    private ASTNode factor() {
        ASTNode expr = unary();

        while (match(TokenType.AND) || match(TokenType.NAND)) {
            Token operator = previous();
            ASTNode right = unary();
            expr = new BinaryOpNode(operator.type, expr, right);
        }

        return expr;
    }

    private ASTNode unary() {
        if (match(TokenType.NOT)) {
            Token operator = previous();
            ASTNode right = unary();
            return new UnaryOpNode(operator.type, right);
        }

        return primary();
    }

    private ASTNode primary() {
        if (match(TokenType.TRUE)) {
            return new LiteralNode(true);
        }

        if (match(TokenType.FALSE)) {
            return new LiteralNode(false);
        }

        if (match(TokenType.INTEGER)) {
            String value = previous().lexeme;
            return new LiteralNode(value.equals("1"));
        }

        if (match(TokenType.VARIABLE)) {
            return new VariableNode(previous().lexeme);
        }

        if (match(TokenType.LPAREN)) {
            ASTNode expr = expression();
            consume(TokenType.RPAREN, "Expected ')' after expression");
            return expr;
        }

        throw new RuntimeException("Expected expression, got " + peek().type);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) position++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(position);
    }

    private Token previous() {
        return tokens.get(position - 1);
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException(message);
    }
}
