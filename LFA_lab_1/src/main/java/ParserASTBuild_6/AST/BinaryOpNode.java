package ParserASTBuild_6.AST;

import LexerScanner_3.TokenType;

public class BinaryOpNode implements ASTNode {
    private final TokenType operator;
    private final ASTNode left;
    private final ASTNode right;

    public BinaryOpNode(TokenType operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(ExpressionContext context) {
        boolean leftValue = left.evaluate(context);
        boolean rightValue = right.evaluate(context);

        switch (operator) {
            case AND:
                return leftValue && rightValue;
            case OR:
                return leftValue || rightValue;
            case NAND:
                return !(leftValue && rightValue);
            case NOR:
                return !(leftValue || rightValue);
            case XOR:
                return leftValue ^ rightValue;
            case XNOR:
                return !(leftValue ^ rightValue);
            default:
                throw new RuntimeException("Invalid binary operator: " + operator);
        }
    }

    @Override
    public String toString() {
        return "(" + left + " " + operator + " " + right + ")";
    }
}
