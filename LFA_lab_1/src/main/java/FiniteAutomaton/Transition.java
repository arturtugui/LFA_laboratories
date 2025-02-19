package FiniteAutomaton;

import Grammar.symbols.Symbol;

public class Transition {
    public Symbol fromState;
    public Symbol symbol;
    public Symbol toState;

    public Transition(Symbol fromState, Symbol symbol, Symbol toState) {
        this.fromState = fromState;
        this.symbol = symbol;
        this.toState = toState;
    }
}
