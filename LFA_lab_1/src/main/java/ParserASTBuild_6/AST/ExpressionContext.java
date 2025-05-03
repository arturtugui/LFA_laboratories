package ParserASTBuild_6.AST;

public class ExpressionContext {
    private final java.util.Map<String, Boolean> variables = new java.util.HashMap<>();

    public void setVariableValue(String name, boolean value) {
        variables.put(name, value);
    }

    public boolean getVariableValue(String name) {
        if (!variables.containsKey(name)) {
            throw new RuntimeException("Undefined variable: " + name);
        }
        return variables.get(name);
    }
}
