package ParserASTBuild_6.AST;

public class LiteralNode implements ASTNode {
    private final boolean value;

    public LiteralNode(boolean value) {
        this.value = value;
    }

    @Override
    public boolean evaluate(ExpressionContext context) {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
