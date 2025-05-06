package Grammar.symbols;

import java.util.Objects;

public class Symbol {
    public String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(name, symbol.name) && this.getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, this.getClass());
    }
}