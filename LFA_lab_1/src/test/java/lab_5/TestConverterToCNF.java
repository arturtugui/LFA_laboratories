package lab_5;

import Grammar.Grammar;
import Grammar.Production;
import Grammar.symbols.Epsilon;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Terminal;
import lab_5.ContextFreeGrammar.ConverterToCNF;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestConverterToCNF {
    @Test
    public void removeSelfLoopProductionsTest() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(a, B)));
        productions.add(new Production(List.of(S), List.of(A)));
        productions.add(new Production(List.of(A), List.of(A)));
        productions.add(new Production(List.of(A), List.of(a)));
        productions.add(new Production(List.of(B), List.of(B, A, b, B)));
        productions.add(new Production(List.of(B), List.of(B)));
        productions.add(new Production(List.of(B), List.of(b)));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);

        ConverterToCNF.removeSelfLoopProductions(grammar);

        Set<Production> expected = new HashSet<>();
        expected.add(new Production(List.of(S), List.of(a, B)));
        expected.add(new Production(List.of(S), List.of(A)));
        expected.add(new Production(List.of(A), List.of(a)));
        expected.add(new Production(List.of(B), List.of(B, A, b, B)));
        expected.add(new Production(List.of(B), List.of(b)));

        Set<Production> actual = new HashSet<>(grammar.productions);

        assertEquals(expected, actual);
    }

    @Test
    public void removeEpsilonProductionsTest2() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A, B, C, D)));
        productions.add(new Production(List.of(D), List.of(b)));
        productions.add(new Production(List.of(A), List.of(B, C)));
        productions.add(new Production(List.of(A), List.of(a)));
        productions.add(new Production(List.of(B), List.of(new Epsilon())));
        productions.add(new Production(List.of(C), List.of(new Epsilon())));


        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        ConverterToCNF.removeEpsilonProductions(grammar);

        Set<Production> expected = new HashSet<>();
        expected.add(new Production(List.of(S), List.of(A, B, C, D)));
        expected.add(new Production(List.of(S), List.of(B, C, D)));
        expected.add(new Production(List.of(S), List.of(A, C, D)));
        expected.add(new Production(List.of(S), List.of(A, B, D)));
        expected.add(new Production(List.of(S), List.of(A, D)));
        expected.add(new Production(List.of(S), List.of(B, D)));
        expected.add(new Production(List.of(S), List.of(C, D)));
        expected.add(new Production(List.of(S), List.of(D)));
        expected.add(new Production(List.of(D), List.of(b)));
        expected.add(new Production(List.of(A), List.of(a)));

        Set<Production> actual = new HashSet<>(grammar.productions);


        assertEquals(expected, actual);

    }

    @Test
    public void removeEpsilonProductionsTest1() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A, D)));
        productions.add(new Production(List.of(S), List.of(A)));
        productions.add(new Production(List.of(S), List.of(a)));
        productions.add(new Production(List.of(A), List.of(a)));
        productions.add(new Production(List.of(B), List.of(b)));
        productions.add(new Production(List.of(A), List.of(B, C)));
        productions.add(new Production(List.of(B), List.of(new Epsilon())));
        productions.add(new Production(List.of(C), List.of(new Epsilon())));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);

        ConverterToCNF.removeEpsilonProductions(grammar);

        Set<Production> expected = new HashSet<>();
        expected.add(new Production(List.of(S), List.of(A, D)));
        expected.add(new Production(List.of(S), List.of(a)));
        expected.add(new Production(List.of(A), List.of(a)));
        expected.add(new Production(List.of(B), List.of(b)));
        expected.add(new Production(List.of(S), List.of(D)));
        expected.add(new Production(List.of(S), List.of(new Epsilon())));

        Set<Production> actual = new HashSet<>(grammar.productions);


        assertEquals(expected, actual);
    }

    @Test
    public void removeUnitProductionsTest1(){
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(a, B)));
        productions.add(new Production(List.of(S), List.of(A)));

        productions.add(new Production(List.of(A), List.of(a)));
        productions.add(new Production(List.of(A), List.of(a, B, A, b)));

        productions.add(new Production(List.of(B), List.of(S)));
        productions.add(new Production(List.of(B), List.of(B, S)));

        productions.add(new Production(List.of(C), List.of(A)));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.removeUnitProductions(grammar);
        grammar.displayGrammarType2Or3();
    }

    @Test
    public void removeUnitProductionsTest2(){
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A)));
        productions.add(new Production(List.of(S), List.of(a)));

        productions.add(new Production(List.of(A), List.of(B)));
        productions.add(new Production(List.of(A), List.of(a)));

        productions.add(new Production(List.of(B), List.of(C)));
        productions.add(new Production(List.of(B), List.of(b)));

        productions.add(new Production(List.of(C), List.of(b)));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.removeUnitProductions(grammar);
        grammar.displayGrammarType2Or3();
    }

    @Test
    public void removeUnitProductionsTest3(){
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A)));
        productions.add(new Production(List.of(S), List.of(a)));

        productions.add(new Production(List.of(A), List.of(B)));
        productions.add(new Production(List.of(A), List.of(a)));

        productions.add(new Production(List.of(B), List.of(C)));
        productions.add(new Production(List.of(B), List.of(b)));

        productions.add(new Production(List.of(C), List.of(D)));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.removeUnitProductions(grammar);
        grammar.displayGrammarType2Or3();
    }

    @Test
    public void removeNonexistingSymbolsTest1(){
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");
        Terminal c = new Terminal("c");
        Terminal d = new Terminal("d");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D);
        Set<Terminal> terminals = Set.of(a, b, c, d);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A)));
        productions.add(new Production(List.of(S), List.of(a, C)));

        productions.add(new Production(List.of(A), List.of(d)));
        productions.add(new Production(List.of(A), List.of(a)));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.recomputeSymbols(grammar);
        grammar.displayGrammarType2Or3();
    }

    @Test
    public void removeInaccessibleSymbolsTest1() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A, B, C, D)));
        productions.add(new Production(List.of(D), List.of(b)));
        productions.add(new Production(List.of(A), List.of(B, C)));
        productions.add(new Production(List.of(A), List.of(a)));
        productions.add(new Production(List.of(B), List.of(new Epsilon())));
        productions.add(new Production(List.of(C), List.of(new Epsilon())));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.removeInaccessibleSymbols(grammar);
        grammar.displayGrammarType2Or3();
    }

    @Test
    public void removeInaccessibleSymbolsTest2() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");
        Terminal c = new Terminal("c");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D);
        Set<Terminal> terminals = Set.of(a, b, c);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A)));
        productions.add(new Production(List.of(A), List.of(a, B)));
        productions.add(new Production(List.of(B), List.of(B, b)));
        productions.add(new Production(List.of(B), List.of(b)));
        productions.add(new Production(List.of(B), List.of(new Epsilon())));
        productions.add(new Production(List.of(D), List.of(new Epsilon())));
        productions.add(new Production(List.of(D), List.of(c)));

        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.removeInaccessibleSymbols(grammar);
        grammar.displayGrammarType2Or3();
    }


    @Test
    public void removeUnproductiveSymbolsTest1() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");
        NonTerminal E = new NonTerminal("E");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D, E);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(A)));
        productions.add(new Production(List.of(S), List.of(A, B)));
        productions.add(new Production(List.of(A), List.of(a)));
        productions.add(new Production(List.of(B), List.of(C, D)));
        productions.add(new Production(List.of(C), List.of(b)));
        productions.add(new Production(List.of(D), List.of(E)));



        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.removeUnproductiveSymbols(grammar);
        grammar.displayGrammarType2Or3();
    }

    @Test
    public void removeUnproductiveSymbolsTest2() {
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");
        NonTerminal E = new NonTerminal("E");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D, E);
        Set<Terminal> terminals = Set.of(a, b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(a)));
        productions.add(new Production(List.of(S), List.of(A, B)));
        productions.add(new Production(List.of(S), List.of(D)));
        productions.add(new Production(List.of(A), List.of(a)));
        productions.add(new Production(List.of(B), List.of(C)));
        productions.add(new Production(List.of(C), List.of(b)));
        productions.add(new Production(List.of(D), List.of(E)));
        productions.add(new Production(List.of(E), List.of(D)));



        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);
        grammar.displayGrammarType2Or3();
        ConverterToCNF.removeUnproductiveSymbols(grammar);
        grammar.displayGrammarType2Or3();
    }

    @Test
    public void variant21(){
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");
        Terminal d = new Terminal("d");

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(List.of(S), List.of(d, B)));
        productions.add(new Production(List.of(S), List.of(A, C)));
        productions.add(new Production(List.of(A), List.of(d)));
        productions.add(new Production(List.of(A), List.of(d, S)));
        productions.add(new Production(List.of(A), List.of(a, B, d, B)));
        productions.add(new Production(List.of(B), List.of(a)));
        productions.add(new Production(List.of(B), List.of(a, A)));
        productions.add(new Production(List.of(B), List.of(A, C)));
        productions.add(new Production(List.of(C), List.of(b, C)));
        productions.add(new Production(List.of(C), List.of(new Epsilon())));
        productions.add(new Production(List.of(D), List.of(a, b)));

        Set<NonTerminal> nonTerminals = Set.of(S, A, B, C, D);
        Set<Terminal> terminals = Set.of(a, b, d);
        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);


        grammar.displayGrammarType2Or3();

//        there are none
//        ConverterToCNF.removeSelfLoopProductions(grammar);
//        grammar.displayGrammarType2Or3();

        System.out.println("\t1. Removing epsilon productions:");
        ConverterToCNF.removeEpsilonProductions(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t2. Removing unit productions:");
        ConverterToCNF.removeUnitProductions(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t3. Removing inaccessible symbols:");
        ConverterToCNF.removeInaccessibleSymbols(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t4. Removing unproductive rules:");
        ConverterToCNF.removeUnproductiveSymbols(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t5.1. Obtaining CNF. Eliminating Productions With Nonsolitary Terminals:");
        ConverterToCNF.currentX = 1;
        ConverterToCNF.eliminateProductionsWithNonsolitaryTerminals(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t5.2. Obtaining CNF. Eliminating RHS bigger than two:");
        ConverterToCNF.eliminateLargeRHS(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t6. Checking for S0");
        ConverterToCNF.checkForS0(grammar);
        grammar.displayGrammarType2Or3();
    }
}
