package ParserASTBuild_6;

import LexerScanner_3.Token;
import LexerScanner_3.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexLexer {
    private final String input;
    private int position = 0;

    // Define regex patterns for each token type
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("^\\s+");
    private static final Pattern TRUE_PATTERN = Pattern.compile("^true");
    private static final Pattern FALSE_PATTERN = Pattern.compile("^false");
    private static final Pattern AND_PATTERN = Pattern.compile("^AND");
    private static final Pattern OR_PATTERN = Pattern.compile("^OR");
    private static final Pattern NOT_PATTERN = Pattern.compile("^NOT");
    private static final Pattern NAND_PATTERN = Pattern.compile("^NAND");
    private static final Pattern NOR_PATTERN = Pattern.compile("^NOR");
    private static final Pattern XOR_PATTERN = Pattern.compile("^XOR");
    private static final Pattern XNOR_PATTERN = Pattern.compile("^XNOR");
    private static final Pattern LPAREN_PATTERN = Pattern.compile("^\\(");
    private static final Pattern RPAREN_PATTERN = Pattern.compile("^\\)");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[01]");
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public RegexLexer(String input) {
        this.input = input;
    }

    private String remaining() {
        return position < input.length() ? input.substring(position) : "";
    }

    private int matchAndAdvance(Pattern pattern) {
        Matcher matcher = pattern.matcher(remaining());
        if (matcher.find()) {
            String match = matcher.group();
            position += match.length();
            return match.length();
        }
        return 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            String remainingInput = remaining();

            // Skip whitespace
            if (matchAndAdvance(WHITESPACE_PATTERN) > 0) {
                continue;
            }

            // Match tokens using regex patterns
            if (matchAndAdvance(TRUE_PATTERN) > 0) {
                tokens.add(new Token(TokenType.TRUE, "true"));
            }
            else if (matchAndAdvance(FALSE_PATTERN) > 0) {
                tokens.add(new Token(TokenType.FALSE, "false"));
            }
            else if (matchAndAdvance(AND_PATTERN) > 0) {
                tokens.add(new Token(TokenType.AND, "AND"));
            }
            else if (matchAndAdvance(OR_PATTERN) > 0) {
                tokens.add(new Token(TokenType.OR, "OR"));
            }
            else if (matchAndAdvance(NOT_PATTERN) > 0) {
                tokens.add(new Token(TokenType.NOT, "NOT"));
            }
            else if (matchAndAdvance(NAND_PATTERN) > 0) {
                tokens.add(new Token(TokenType.NAND, "NAND"));
            }
            else if (matchAndAdvance(NOR_PATTERN) > 0) {
                tokens.add(new Token(TokenType.NOR, "NOR"));
            }
            else if (matchAndAdvance(XOR_PATTERN) > 0) {
                tokens.add(new Token(TokenType.XOR, "XOR"));
            }
            else if (matchAndAdvance(XNOR_PATTERN) > 0) {
                tokens.add(new Token(TokenType.XNOR, "XNOR"));
            }
            else if (matchAndAdvance(LPAREN_PATTERN) > 0) {
                tokens.add(new Token(TokenType.LPAREN, "("));
            }
            else if (matchAndAdvance(RPAREN_PATTERN) > 0) {
                tokens.add(new Token(TokenType.RPAREN, ")"));
            }
            else if (matchAndAdvance(INTEGER_PATTERN) > 0) {
                // We already matched either 0 or 1
                tokens.add(new Token(TokenType.INTEGER, remainingInput.substring(0, 1)));
            }
            else {
                // Try to match variable name
                Matcher varMatcher = VARIABLE_PATTERN.matcher(remainingInput);
                if (varMatcher.find()) {
                    String varName = varMatcher.group();
                    tokens.add(new Token(TokenType.VARIABLE, varName));
                    position += varName.length();
                } else {
                    throw new RuntimeException("Unexpected character: " + remainingInput.charAt(0));
                }
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}