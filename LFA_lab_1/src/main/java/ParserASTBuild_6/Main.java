package ParserASTBuild_6;

import LexerScanner_3.Token;
import LexerScanner_3.TokenType;
import ParserASTBuild_6.AST.ASTNode;
import ParserASTBuild_6.AST.ExpressionContext;
import ParserASTBuild_6.Parser;

import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Run demo examples");
        System.out.println("2. Enter custom boolean expression");
        System.out.print("Your choice (1 or 2): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            runDemoExamples();
        } else {
            System.out.println("\nEnter a boolean expression (using operators: AND, OR, NOT, NAND, NOR, XOR, XNOR):");
            System.out.println("Variables should be named like var_name, constants can be 'true', 'false', '0', or '1'");
            System.out.print("> ");
            String input = scanner.nextLine();

            processExpression(input);

            // Ask for variable values
            System.out.println("\nWould you like to evaluate the expression? (y/n)");
            String evalChoice = scanner.nextLine();

            if (evalChoice.equalsIgnoreCase("y")) {
                evaluateWithUserInput(input, scanner);
            }
        }

        scanner.close();
    }

    private static void runDemoExamples() {
        String[] examples = {
                "true AND false",
                "var_x OR (NOT var_y)",
                "false XNOR (var_1 OR 0)",
                "(var_a AND var_b) OR (var_c AND NOT var_d)",
                "NOT (var_x XOR var_y) AND (var_z NOR false)"
        };

        for (int i = 0; i < examples.length; i++) {
            System.out.println("\n=== Example " + (i+1) + " ===");
            processExpression(examples[i]);
        }
    }

    private static void processExpression(String input) {
        try {
            // Lexical analysis - tokenize the input
            RegexLexer lexer = new RegexLexer(input);
            List<Token> tokens = lexer.tokenize();

            // Print the input and tokens
            System.out.println("Input expression: " + input);
            System.out.println("\nTokens:");
            tokens.forEach(System.out::println);

            // Parse the tokens to build an AST
            Parser parser = new Parser(tokens);
            ASTNode ast = parser.parse();

            // Print the AST structure
            System.out.println("\nAST Structure:");
            System.out.println(ast);
        } catch (Exception e) {
            System.out.println("\nError processing expression: " + e.getMessage());
        }
    }

    private static void evaluateWithUserInput(String input, Scanner scanner) {
        try {
            // Tokenize and parse
            RegexLexer lexer = new RegexLexer(input);
            List<Token> tokens = lexer.tokenize();
            Parser parser = new Parser(tokens);
            ASTNode ast = parser.parse();

            // Extract variables from tokens
            Map<String, Boolean> variables = new HashMap<>();
            for (Token token : tokens) {
                if (token.type == TokenType.VARIABLE && !variables.containsKey(token.lexeme)) {
                    System.out.print("Enter value for '" + token.lexeme + "' (true/false): ");
                    String value = scanner.nextLine();
                    variables.put(token.lexeme, Boolean.parseBoolean(value));
                }
            }

            // Set up context and evaluate
            ExpressionContext context = new ExpressionContext();
            for (Map.Entry<String, Boolean> entry : variables.entrySet()) {
                context.setVariableValue(entry.getKey(), entry.getValue());
            }

            boolean result = ast.evaluate(context);
            System.out.println("\nEvaluation result: " + result);
        } catch (Exception e) {
            System.out.println("\nError evaluating expression: " + e.getMessage());
        }
    }
}
