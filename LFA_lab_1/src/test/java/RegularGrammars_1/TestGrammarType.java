package RegularGrammars_1;

import Grammar.Grammar;
import Grammar.Production;
import Grammar.symbols.Epsilon;
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
    public void TestMyVariant() {
        /*
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
        System.out.println("S → aB | B → bS | B → aC | B → c | C → bD | D → c | D → aC");

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
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a, nt_B))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(t_b, nt_S))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(t_a, nt_C))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(t_c))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_C)), new ArrayList<Symbol>(List.of(t_b, nt_D))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_D)), new ArrayList<Symbol>(List.of(t_c))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_D)), new ArrayList<Symbol>(List.of(t_a, nt_C))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(3, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType3RightLinear() {
        /*
         V_N = {S, B, C, D}
         V_T = {a, b, c}
         P = {
             S → aS,
             S → b
             }
         */
        System.out.println("S → aS | S → b");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        nonTerminals.add(nt_S);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a, nt_S))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_b))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(3, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType3LeftLinear() {
        /*
         V_N = {S, B, C, D}
         V_T = {a, b, c}
         P = {
             S → Sa,
             S → b
             }
         */
        System.out.println("S → Sa | S → b");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        nonTerminals.add(nt_S);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(nt_S, t_a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_b))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(3, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType3WithEpsilon() {
        /*
         V_N = {S, C}
         V_T = {a}
         P = {
             S → a,
             S → ε
         }
         */
        System.out.println("S → a | S → ε");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        nonTerminals.add(nt_S);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        terminals.add(t_a);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(new Epsilon()))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(3, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType3MultipleTerminals() {
        /*
         V_N = {S}
         V_T = {a, b}
         P = {
             S → abS,
             S → a
         }
         */
        System.out.println("S → abS | S → a");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        nonTerminals.add(nt_S);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a, t_b, nt_S))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(3, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType3NoNonTerminal() {
        /*
         V_N = {S}
         V_T = {a, b}
         P = {
             S → a,
             S → b
         }
         */
        System.out.println("S → a | S → b");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        nonTerminals.add(nt_S);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_b))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(3, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }


    @Test
    public void TestType2TooManyNonTerminals() {
        /*
         V_N = {S, B, C}
         V_T = {a, b}
         P = {
             S → aBC,
             C → abB,
             C → a,
             B → b
         }
         */
        System.out.println("S → aBC | C → abB | C → a | B → b");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        NonTerminal nt_B = new NonTerminal("B");
        NonTerminal nt_C = new NonTerminal("C");
        nonTerminals.add(nt_S);
        nonTerminals.add(nt_B);
        nonTerminals.add(nt_C);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a, nt_B, nt_C))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_C)), new ArrayList<Symbol>(List.of(t_a, t_b, nt_B))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_C)), new ArrayList<Symbol>(List.of(t_a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(t_b))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(2, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType2DifferentSides() {
        /*
         V_N = {S}
         V_T = {a, b, d}
         P = {
             S → aS,
             S → b,
             S → Sd,
             S → ε
         }
         */
        System.out.println("S → aS | S → b | S → Sd | S → ε");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        nonTerminals.add(nt_S);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        Terminal t_d = new Terminal("d");
        terminals.add(t_a);
        terminals.add(t_b);
        terminals.add(t_d);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a, nt_S))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_b))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(nt_S, t_d))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(new Epsilon()))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(2, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType2MiddleNonTerminal() {
        /*
         V_N = {S, A}
         V_T = {a, b}
         P = {
             S → aAb,
             A → a
         }
         */
        System.out.println("S → aAb | A → a");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        NonTerminal nt_A = new NonTerminal("A");
        nonTerminals.add(nt_S);
        nonTerminals.add(nt_A);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a, nt_A, t_b))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_A)), new ArrayList<Symbol>(List.of(t_a))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(2, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType1MultipleOnLHS() {
    /*
     V_N = {S, A, B, C}
     V_T = {a, b, c}
     P = {
         S → ABC | ABCS,
         AB → BA,
         C → c,
         A → a,
         B → b
     }
     */

        System.out.println("S → ABC | S → ABCS | AB → BA | C → c | A → a | B → b");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        NonTerminal nt_A = new NonTerminal("A");
        NonTerminal nt_B = new NonTerminal("B");
        NonTerminal nt_C = new NonTerminal("C");
        nonTerminals.add(nt_S);
        nonTerminals.add(nt_A);
        nonTerminals.add(nt_B);
        nonTerminals.add(nt_C);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        Terminal t_c = new Terminal("c");
        terminals.add(t_a);
        terminals.add(t_b);
        terminals.add(t_c);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(nt_A, nt_B, nt_C))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(nt_A, nt_B, nt_C, nt_S))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_A, nt_B)), new ArrayList<Symbol>(List.of(nt_B, nt_A))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_C)), new ArrayList<Symbol>(List.of(t_c))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_A)), new ArrayList<Symbol>(List.of(t_a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(t_b))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(1, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestType0EpsilonRHS() {
    /*
     V_N = {S, B, C}
     V_T = {a, b}
     P = {
         S → aSBC,
         bC → Bab,
         C → a,
         B → b,
         B → ε
     }
     */
        System.out.println("S → aSBC | bC → Bab | C → a | B → b | B → ε");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        NonTerminal nt_B = new NonTerminal("B");
        NonTerminal nt_C = new NonTerminal("C");
        nonTerminals.add(nt_S);
        nonTerminals.add(nt_B);
        nonTerminals.add(nt_C);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(t_a, nt_S, nt_B, nt_C))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B, nt_C)), new ArrayList<Symbol>(List.of(t_b, nt_B, t_a, t_b))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_C)), new ArrayList<Symbol>(List.of(t_a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(t_b))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(new Epsilon()))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(0, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }

    @Test
    public void TestNotValidGrammar() {
        /*
         V_N = {S, B, A}
         V_T = {a, b}
         P = {
             S → Aε,
             ε → AB,
             A → a,
             B → b
         }
         */
        System.out.println("S → Aε | ε → AB | A → a | B → b");

        Set<NonTerminal> nonTerminals = new HashSet<>();
        NonTerminal nt_S = new NonTerminal("S");
        NonTerminal nt_A = new NonTerminal("A");
        NonTerminal nt_B = new NonTerminal("B");
        nonTerminals.add(nt_S);
        nonTerminals.add(nt_A);
        nonTerminals.add(nt_B);

        Set<Terminal> terminals = new HashSet<>();
        Terminal t_a = new Terminal("a");
        Terminal t_b = new Terminal("b");
        terminals.add(t_a);
        terminals.add(t_b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_S)), new ArrayList<Symbol>(List.of(nt_A, new Epsilon()))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(new Epsilon())), new ArrayList<Symbol>(List.of(nt_A, nt_B))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_A)), new ArrayList<Symbol>(List.of(t_a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(nt_B)), new ArrayList<Symbol>(List.of(t_b))));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, nt_S);

        assertEquals(-1, Grammar.classifyGrammar(grammar));
        System.out.print("\n");
    }
}
