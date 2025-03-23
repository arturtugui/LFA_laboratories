package lab_3;

public enum TokenType {
    TRUE, FALSE, // boolean literals
    AND, OR, NOT, // basic logic operators
    NAND, NOR, XOR, XNOR, // other logic operators
    LPAREN, RPAREN, // parantheses
    INTEGER, VARIABLE, // Integer(0,1) and boolean variables
    EOF
}
