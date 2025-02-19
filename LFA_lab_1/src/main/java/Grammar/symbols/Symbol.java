package Grammar.symbols;

public class Symbol {
    public String name;

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
