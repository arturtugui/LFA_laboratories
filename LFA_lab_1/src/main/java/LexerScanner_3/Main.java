package LexerScanner_3;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "false XNOR (var_1 OR 0)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        System.out.println("The input: " + input + "\n\n");

        System.out.println("The identified tokens:");
        tokens.forEach(System.out::println);
    }
}
