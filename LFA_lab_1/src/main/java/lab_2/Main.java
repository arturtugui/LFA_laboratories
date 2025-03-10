package lab_2;

import FiniteAutomaton.FiniteAutomaton;
import FiniteAutomaton.Transition;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Terminal;
import Grammar.Grammar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<NonTerminal> states = new HashSet<>();
        NonTerminal q0 = new NonTerminal("q0");
        NonTerminal q1 = new NonTerminal("q1");
        NonTerminal q2 = new NonTerminal("q2");
        states.add(q0);
        states.add(q1);
        states.add(q2);

        Set<Terminal> alphabet = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        Terminal t_c = new Terminal("c");
        alphabet.add(t_a);
        alphabet.add(t_b);
        alphabet.add(t_c);

        List<Transition> transitions = new ArrayList<>();
        transitions.add(new Transition(q0, t_a, q0));
        transitions.add(new Transition(q0, t_a, q1));
        transitions.add(new Transition(q1, t_a, q0));
        transitions.add(new Transition(q1, t_c, q1));
        transitions.add(new Transition(q1, t_b, q2));
        transitions.add(new Transition(q2, t_a, q2));

        FiniteAutomaton finiteAutomaton = new FiniteAutomaton(states, alphabet, transitions, q0, q2);
        finiteAutomaton.displayFiniteAutomaton();

        Grammar grammar = finiteAutomaton.toRegularGrammar();
        grammar.displayGrammar();


        if (finiteAutomaton.isDeterministic()) {
            System.out.println("FA is deterministic\n");
        }
        else {
            System.out.println("FA is  not deterministic\n");
        }

        FiniteAutomaton dfa = finiteAutomaton.convertToDFA();
        dfa.displayFiniteAutomaton();


    }
}
