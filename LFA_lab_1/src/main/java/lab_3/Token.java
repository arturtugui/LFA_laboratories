package lab_3;

public class Token {
    TokenType type;
    String lexeme;

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + lexeme + "'}";
    }
}
