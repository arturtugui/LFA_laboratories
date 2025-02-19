import FiniteAutomaton.FiniteAutomaton;
import Grammar.Grammar;
import Grammar.Production;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        NonTerminal nt_B = new NonTerminal("B");
        NonTerminal nt_C = new NonTerminal("C");
        NonTerminal nt_D = new NonTerminal("D");
        nonTerminals.add(nt_S);
        nonTerminals.add(nt_B);
        nonTerminals.add(nt_C);
        nonTerminals.add(nt_D);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        Terminal t_c = new Terminal("c");
        terminals.add(t_a);
        terminals.add(t_b);
        terminals.add(t_c);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(nt_S, new ArrayList<Symbol>(List.of(t_a, nt_B))));
        productions.add(new Production(nt_B, new ArrayList<Symbol>(List.of(t_b, nt_S))));
        productions.add(new Production(nt_B, new ArrayList<Symbol>(List.of(t_a, nt_C))));
        productions.add(new Production(nt_B, new ArrayList<Symbol>(List.of(t_c))));
        productions.add(new Production(nt_C, new ArrayList<Symbol>(List.of(t_b, nt_D))));
        productions.add(new Production(nt_D, new ArrayList<Symbol>(List.of(t_c))));
        productions.add(new Production(nt_D, new ArrayList<Symbol>(List.of(t_a, nt_C))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        System.out.println("\n\t Words generated based on this Grammar:");
        for(int i = 0; i < 5; i++){
            grammar.generateString();
            System.out.print("\n");
        }

        FiniteAutomaton fa = grammar.toFiniteAutomaton();
        //fa.displayFiniteAutomaton();

        System.out.println("\n\t Can these words be obtain from the Finite Automaton:");
        ArrayList<String> testWords = new ArrayList<>();
        testWords.add("ac");
        testWords.add("aabc");
        testWords.add("abac");
        testWords.add("aabababababc");
        testWords.add("bc");
        testWords.add("ab");
        testWords.add("c");

        for(String tword : testWords){
            System.out.println(tword + " -> " + fa.stringBelongToLanguage(tword));
        }


    }
}
