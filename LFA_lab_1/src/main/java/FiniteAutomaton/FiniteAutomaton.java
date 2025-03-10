package FiniteAutomaton;

import Grammar.symbols.Epsilon;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;
import Grammar.Grammar;
import Grammar.Production;

import java.util.*;

public class FiniteAutomaton {
    public Set<NonTerminal> states;
    public Set<Terminal> alphabet;
    public List<Transition> transitions;
    public NonTerminal initialState;
    public NonTerminal finalState;

    public FiniteAutomaton(Set<NonTerminal> states, Set<Terminal> alphabet, List<Transition> transitions, NonTerminal initialState, NonTerminal finalState) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.initialState = initialState;
        this.finalState = finalState;
    }

    public boolean stringBelongToLanguage(String inputString)
    {
        Symbol currentState = new Symbol(initialState.name);

        for (char ch : inputString.toCharArray()){
            Transition candidateTransition = null;

            //System.out.println(ch);

            for (Transition t : transitions) {
                if (t.fromState.name.equals(currentState.name) && t.symbol.name.charAt(0) == ch) {
                    //System.out.println("candidate");
                    candidateTransition = t;
                    break;
                }
            }

            if (candidateTransition != null) {
                //System.out.println("perfect");
                //System.out.println(candidateTransition.fromState.name + " --" + candidateTransition.symbol.name + "--> " + candidateTransition.toState.name);
                currentState.name = candidateTransition.toState.name;
                //System.out.println(currentState.name);
            }
            else {
                //System.out.println("not perfect");
                return false;
            }
        }

        if (Objects.equals(finalState.name, currentState.name)) {
            return true;
        }

        return false;
    }

    public void displayFiniteAutomaton() {
        System.out.println("\tFinite Automaton:");
        // Display states
        System.out.println("States:");
        for (NonTerminal state : states) {
            System.out.println(state.name);
        }

        // Display alphabet
        System.out.println("\nAlphabet:");
        for (Terminal terminal : alphabet) {
            System.out.println(terminal.name);
        }

        // Display transitions
        System.out.println("\nTransitions:");
        for (Transition transition : transitions) {
            System.out.println(transition.fromState.name + " --" + transition.symbol.name + "--> " + transition.toState.name);
        }

        // Display initial and final states
        System.out.println("\nInitial State: " + initialState.name);
        System.out.println("Final State: " + finalState.name);

        System.out.println("\n");
    }

    public Grammar toRegularGrammar(){
        List<Production> productions = generateProductions(this.transitions, this.finalState);

        Grammar grammar = new Grammar(states, alphabet, productions, initialState);

        return grammar;
    }

    public static List<Production> generateProductions(List<Transition> transitions, NonTerminal finalState){
        List<Production> productions = new ArrayList<>();
        List<Symbol> left, right;

        for (Transition tran : transitions) {
            left = new ArrayList<Symbol>(List.of(tran.fromState));
            right = new ArrayList<Symbol>(List.of(tran.symbol ,tran.toState));
            productions.add(new Production(left, right));
        }

        left = new ArrayList<Symbol>(List.of(finalState));
        right = new ArrayList<Symbol>(List.of(new Epsilon()));
        productions.add(new Production(left, right));

        return productions;
    }

    public boolean isDeterministic() {
        for(Transition t1 : transitions){
            if(t1.symbol instanceof Epsilon){
                return false;
            }

            for(Transition t2 : transitions){
                if(t1.fromState.equals(t2.fromState) &&  t1.symbol.equals(t2.symbol) && t1 != t2){
                    return false;
                }
            }
        }

        return true;
    }

    public FiniteAutomaton convertToDFA() {
        if (this.isDeterministic()){
            System.out.println("The FA is already deterministic");
            return null;
        }

        Map<Set<NonTerminal>, NonTerminal> dfaStates = new HashMap<>(); // mult. NFA states <-> one DFA state
        List<Transition> dfaTransitions = new ArrayList<>();
        Queue<Set<NonTerminal>> queue = new LinkedList<>();


        //add initial state
        Set<NonTerminal> initialSet = new HashSet<>();
        initialSet.add(initialState);
        queue.add(initialSet);
        dfaStates.put(initialSet, new NonTerminal("Q0"));

        int stateCounter = 1;

        while (!queue.isEmpty()) {
            Set<NonTerminal> currentSet = queue.poll(); // NFA states
            NonTerminal dfaState = dfaStates.get(currentSet); // of the DFA state

            for (Terminal symbol : alphabet) {
                Set<NonTerminal> newStateSet = new HashSet<>();

                // Find reachable states from `currentSet` on `symbol`
                for (NonTerminal state : currentSet) {
                    for (Transition transition : transitions) {
                        if (transition.fromState.equals(state) && transition.symbol.equals(symbol)) {
                            newStateSet.add((NonTerminal) transition.toState);
                        }
                    }
                }

                if (!newStateSet.isEmpty()) {
                    // Check if this set has already been assigned a DFA state
                    if (!dfaStates.containsKey(newStateSet)) {
                        dfaStates.put(newStateSet, new NonTerminal("Q" + stateCounter++));
                        queue.add(newStateSet);
                    }

                    // Add DFA transition
                    dfaTransitions.add(new Transition(dfaState, symbol, dfaStates.get(newStateSet)));
                }
            }
        }

        // Step 4: Identify DFA final states
        Set<NonTerminal> dfaFinalStates = new HashSet<>();
        for (Set<NonTerminal> set : dfaStates.keySet()) {
            if (set.contains(finalState)) {
                dfaFinalStates.add(dfaStates.get(set));
            }
        }

        return new FiniteAutomaton(new HashSet<>(dfaStates.values()), alphabet, dfaTransitions, dfaStates.get(initialSet), dfaFinalStates.iterator().next());
    }

}
