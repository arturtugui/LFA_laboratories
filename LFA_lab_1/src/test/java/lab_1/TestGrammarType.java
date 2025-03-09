package lab_1;

import Grammar.Grammar;
import Grammar.Production;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestGrammarType {
    @Test
    public void TestType3RightLinear() {
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

        assertEquals(3, Grammar.classifyGrammar(grammar));
    }

    @Test
    public void TestType3LeftLinear() {
        /*
        Variant 30:
        VN={S, B, C, D},
        VT={a, b, c},
        P={
            S → aB
            B → bS
            B → aC
            B → c
            C → bD
            D → c
            D → aC
        }
         */
        
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
        productions.add(new Production(nt_S, new ArrayList<Symbol>(List.of(nt_B, t_a))));
        productions.add(new Production(nt_B, new ArrayList<Symbol>(List.of(nt_S, t_b))));
        productions.add(new Production(nt_B, new ArrayList<Symbol>(List.of(nt_C,t_a))));
        productions.add(new Production(nt_B, new ArrayList<Symbol>(List.of(t_c))));
        productions.add(new Production(nt_C, new ArrayList<Symbol>(List.of(nt_D, t_b))));
        productions.add(new Production(nt_D, new ArrayList<Symbol>(List.of(t_c))));
        productions.add(new Production(nt_D, new ArrayList<Symbol>(List.of(nt_C, t_a))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(3, Grammar.classifyGrammar(grammar));
    }

}
