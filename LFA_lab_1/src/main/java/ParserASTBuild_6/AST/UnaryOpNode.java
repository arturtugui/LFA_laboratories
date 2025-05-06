package ParserASTBuild_6.AST;

import LexerScanner_3.TokenType;

public class UnaryOpNode implements ASTNode {
    private final TokenType operator;
    private final ASTNode operand;

    public UnaryOpNode(TokenType operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public boolean evaluate(ExpressionContext context) {
        boolean operandValue = operand.evaluate(context);

        switch (operator) {
            case NOT:
                return !operandValue;
            default:
                throw new RuntimeException("Invalid unary operator: " + operator);
        }
    }

    @Override
    public String toString() {
        return operator + "(" + operand + ")";
    }
}
