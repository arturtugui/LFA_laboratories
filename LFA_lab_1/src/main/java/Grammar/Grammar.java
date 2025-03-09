package Grammar;

import FiniteAutomaton.FiniteAutomaton;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;
import FiniteAutomaton.Transition;

import java.util.*;

public class Grammar {
    public Set<NonTerminal> nonTerminals;
    public Set<Terminal> terminals;
    public List<Production> productions;
    public NonTerminal startSymbol;

    public Grammar(Set<NonTerminal> nonTerminals, Set<Terminal> terminals, List<Production> productions, NonTerminal startSymbol) {
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = productions;
        this.startSymbol = startSymbol;
    }

    @Override
    public String toString() {
        return "Non-Terminals: " + nonTerminals + "\n" +
                "Terminals: " + terminals + "\n" +
                "Productions:\n" + productions + "\n" +
                "Start Symbol: " + startSymbol;
    }

    public String generateString() {
        StringBuilder sb = new StringBuilder();
        sb.append(startSymbol.name);

        System.out.print(sb.toString());

        recursiveGenerateString(sb);

        String word = sb.toString();
        return word;
    }

    public StringBuilder recursiveGenerateString(StringBuilder sb) {
        Random random = new Random();

        //System.out.print(" -> " + sb.toString());

        for (Production prod : productions) {
            String lhsString = prod.left.stream()
                    .map(symbol -> symbol.name)
                    .reduce((a, b) -> a + b)
                    .orElse("");

            int index = sb.indexOf(lhsString);

            if (index != -1) {
                List<Production> possibleProductions = productions.stream()
                        .filter(p -> p.left.stream()
                                .map(symbol -> symbol.name)
                                .reduce((a, b) -> a + b)
                                .orElse("")
                                .equals(lhsString))
                        .toList();

                if (!possibleProductions.isEmpty()) {
                    int nrOfProductions = possibleProductions.size();
                    int randomIndex = random.nextInt(nrOfProductions);
                    Production chosenProduction = possibleProductions.get(randomIndex);

                    // convert right to a string
                    String rhsString = chosenProduction.right.stream()
                            .map(symbol -> symbol.name)
                            .reduce((a, b) -> a + b)  // put all right symbols in a string
                            .orElse("");

                    sb.replace(index, index + lhsString.length(), rhsString);

                    System.out.print(" -> " + sb.toString());

                    return recursiveGenerateString(sb); // recursion
                }
            }
        }

        return sb;
    }

    public FiniteAutomaton toFiniteAutomaton() {
        //only type 3 grammar can be fa
        if(g)


        NonTerminal finalState = new NonTerminal("final");

        List<Transition> transitions = generateTransitions(productions, finalState);

        FiniteAutomaton fa = new FiniteAutomaton(nonTerminals, terminals, transitions, startSymbol, finalState);

        return fa;
    }

    public static List<Transition> generateTransitions(List<Production> productions, NonTerminal finalState) {
        List<Transition> transitions = new ArrayList<>();

        for (Production p : productions) {

            Symbol terminalSymbol = p.right.stream()
                    .filter(symbol -> symbol instanceof Terminal)
                    .findFirst()
                    .orElse(null);

            Symbol nonTerminalSymbol = p.right.stream()
                    .filter(symbol -> symbol instanceof NonTerminal)
                    .findFirst()
                    .orElse(finalState);

            transitions.add(new Transition(p.left, terminalSymbol,  nonTerminalSymbol));
        }

        return transitions;
    }



}
