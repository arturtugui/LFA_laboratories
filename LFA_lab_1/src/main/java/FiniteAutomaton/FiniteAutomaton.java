package FiniteAutomaton;

import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    }

}
