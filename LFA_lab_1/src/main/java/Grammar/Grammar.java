package Grammar;

import FiniteAutomaton.FiniteAutomaton;
import Grammar.symbols.Epsilon;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;
import FiniteAutomaton.Transition;

import java.util.*;
import java.util.stream.Collectors;

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

    // some type 3 grammar can still have multiple nonterminals, my current implementation just finds the 1st
    public FiniteAutomaton toFiniteAutomaton() {
        // only type 3 grammar can be fa
        if(Grammar.classifyGrammar(this) != 3){
            System.out.println("Only type 3 grammar can be converted to Finite Automaton");
            return null;
        }


        NonTerminal finalState = new NonTerminal("final");

        List<Transition> transitions = generateTransitions(productions, finalState);

        FiniteAutomaton fa = new FiniteAutomaton(nonTerminals, terminals, transitions, startSymbol, finalState);

        return fa;
    }

    public static List<Transition> generateTransitions(List<Production> productions, NonTerminal finalState) {
        List<Transition> transitions = new ArrayList<>();

        for (Production p : productions) {

            Symbol fromState = p.left.getFirst();

            Symbol terminalSymbol = p.right.stream()
                    .filter(symbol -> symbol instanceof Terminal)
                    .findFirst()
                    .orElse(null);

            Symbol nonTerminalSymbol = p.right.stream()
                    .filter(symbol -> symbol instanceof NonTerminal)
                    .findFirst()
                    .orElse(finalState);

            transitions.add(new Transition(fromState, terminalSymbol,  nonTerminalSymbol));
        }

        return transitions;
    }



    /// for lab-2
    public static int classifyGrammar(Grammar grammar) {
        boolean isType0 = true;
        boolean isType1 = true;
        boolean isType2 = true;
        boolean isType3 = true;

        boolean hasEpsilonRHS = false;
        boolean hasMultipleLHS = false;
        int side = 0;

        for(Production prod : grammar.productions){
            //System.out.println(prod.toString() + "   " + prod.left.size());

            if(prod.right.getFirst() instanceof Epsilon) {
                hasEpsilonRHS = true;

                isType1 = false;

                if(hasMultipleLHS) {
                    //System.out.println("type 0");
                    break; // is type 0, type 1 cannot have RHS empty
                }
            }

            if (prod.left.getFirst() instanceof Epsilon) {
                //System.out.println("not valid");
                isType0 = false;
                isType1 = false;
                isType2 = false;
                isType3 = false;
                break; // not a valid grammar, LHS must be non-empty
            }

            else if(prod.left.size() > 1) {
                // can be type 0 or type 1
                hasMultipleLHS = true;
                //System.out.println("type 0 or 1");
                isType2 = false;
                isType3 = false;

                if(hasEpsilonRHS) {
                    //System.out.println("type 0");
                    isType1 = false;
                    break; // is type 0, type 1 cannot have RHS empty
                }
            }

            else {
                // can be type 2 or type 3
                //System.out.println("type 2 or 3");

                // count how many non-terminals in RHS
                long nonTerminalCount = prod.right.stream()
                        .filter(symbol -> symbol instanceof NonTerminal)
                        .count();

                // a. if more than one (not type 3)
                if (nonTerminalCount > 1) {
                    //System.out.println("type 2 c1");
                    isType3 = false; // is type 2
                }

                else if (nonTerminalCount == 0) {
                    //either empty RHS or no nonTerminals nothing can be said
                    continue;
                }

                // find on which side is it
                else {
                    // on the left
                    if(prod.right.get(0) instanceof NonTerminal) {
                        // c. if side changed
                        if(side == 1){
                            //System.out.println("type 2 c2");
                            isType3 = false; // is type 2
                        }

                        side = -1;
                    }

                    // on the right
                    else if(prod.right.get(prod.right.size() - 1) instanceof NonTerminal) {
                        // c. if side changed
                        if(side == -1){
                            //System.out.println("type 2 c3");
                            isType3 = false; // is type 2
                        }

                        side = 1;
                    }

                    // b. if in the middle
                    else {
                        //System.out.println("type 2 c4");
                        isType3 = false; // is type 2
                    }
                }
            }
        }

        //System.out.println(hasEpsilonRHS + " " + hasMultipleLHS);

        if(isType3){
            System.out.println("The grammar is Type 3");
            return 3;
        }
        else if(isType2){
            System.out.println("The grammar is Type 2");
            return 2;
        }
        else if(isType1){
            System.out.println("The grammar is Type 1");
            return 1;
        }
        else if(isType0){
            System.out.println("The grammar is Type 0");
            return 0;
        }
        else{
            System.out.println("This is not a valid grammar (production with empty string - LHS not allowed)");
            return -1;
        }
    }

    public void displayGrammar1() {
        System.out.println("\tGrammar:");
        System.out.println("VN (Non-Terminals): " + nonTerminals);
        System.out.println("VT (Terminals): " + terminals);
        System.out.println("Start Symbol: " + startSymbol);
        System.out.println("Productions:");

        displayProductions1();

        System.out.println("\n");
    }

    public void displayProductions1(){
        for (Production production : productions) {
            String lhs = production.left.stream()
                    .map(Symbol::toString)
                    .reduce((a, b) -> a + b)
                    .orElse("");

            String rhs = production.right.stream()
                    .map(Symbol::toString)
                    .reduce((a, b) -> a + b)
                    .orElse("ε");  // Handle epsilon productions

            System.out.println(lhs + " → " + rhs);
        }
    }

    public void displayGrammarType2Or3() {
        System.out.println("\tGrammar:");
        System.out.println("VN (Non-Terminals): " + nonTerminals);
        System.out.println("VT (Terminals): " + terminals);
        System.out.println("Start Symbol: " + startSymbol);
        System.out.println("Productions:");

        displayProductionsType2Or3();

        System.out.println("\n");
    }

    //needs refactoring
    public void displayProductionsType2Or3() {

        Map<String, Set<String>> productionMap = new HashMap<>();

        for (Production production : productions) {
            String lhs = production.left.stream()
                    .map(Symbol::toString)
                    .reduce((a, b) -> a + b)
                    .orElse("");

            String rhs = production.right.stream()
                    .map(Symbol::toString)
                    .reduce((a, b) -> a + b)
                    .orElse("ε");

            productionMap.computeIfAbsent(lhs, k -> new HashSet<>()).add(rhs);
        }

        List<String> lhsOrder = new ArrayList<>(productionMap.keySet());

        if (lhsOrder.contains("S0")) {
            lhsOrder.remove("S0");
            lhsOrder.add(0, "S0");

            lhsOrder.remove("S");
            lhsOrder.add(1, "S");
        } else if (lhsOrder.contains("S")) {
            lhsOrder.remove("S");
            lhsOrder.add(0, "S");
        }

        for (String lhs : lhsOrder) {
            String rhs = String.join(" | ", productionMap.get(lhs));
            System.out.println(lhs + " → " + rhs);
        }
    }


}
