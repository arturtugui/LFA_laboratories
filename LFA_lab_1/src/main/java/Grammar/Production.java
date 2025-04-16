package Grammar;

import Grammar.symbols.Epsilon;
import Grammar.symbols.Symbol;

import java.util.List;
import java.util.Objects;

public class Production {
    public List<Symbol> left;
    public List<Symbol> right;

    public Production(List<Symbol> left, List<Symbol> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left + " → " + right;
    }

    public boolean isEpsilon() {
        return right.size() == 1 && right.getFirst() instanceof Epsilon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Production)) return false;
        Production that = (Production) o;
        return left.equals(that.left) && right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}

