package ParserASTBuild_6.AST;

public class VariableNode implements ASTNode {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(ExpressionContext context) {
        return context.getVariableValue(name);
    }

    @Override
    public String toString() {
        return name;
    }
}