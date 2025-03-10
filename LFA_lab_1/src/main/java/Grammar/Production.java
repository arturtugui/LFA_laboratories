package Grammar;

import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;

import java.util.List;

public class Production {
    List<Symbol> left;
    List<Symbol> right;

    public Production(List<Symbol> left, List<Symbol> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left + " → " + right;
    }
}
