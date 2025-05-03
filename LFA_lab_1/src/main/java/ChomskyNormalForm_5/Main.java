package ChomskyNormalForm_5;

import Grammar.Production;
import Grammar.symbols.Epsilon;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;
import Grammar.Grammar;
import ChomskyNormalForm_5.ContextFreeGrammar.ConverterToCNF;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Set<NonTerminal> nonTerminals = new HashSet<NonTerminal>();
        NonTerminal S = new NonTerminal("S");
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");
        NonTerminal D = new NonTerminal("D");
        nonTerminals.add(S);
        nonTerminals.add(A);
        nonTerminals.add(B);
        nonTerminals.add(C);
        nonTerminals.add(D);

        Set<Terminal> terminals = new HashSet<Terminal>();
        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");
        terminals.add(a);
        terminals.add(b);

        List<Production> productions = new ArrayList<>();
        productions.add(new Production(new ArrayList<Symbol>(List.of(S)), new ArrayList<Symbol>(List.of(a, B))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(S)), new ArrayList<Symbol>(List.of(A))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(A)), new ArrayList<Symbol>(List.of(a, B, A, b))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(A)), new ArrayList<Symbol>(List.of(a, S))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(A)), new ArrayList<Symbol>(List.of(a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(B)), new ArrayList<Symbol>(List.of(B, A, b, B))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(B)), new ArrayList<Symbol>(List.of(B, S))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(B)), new ArrayList<Symbol>(List.of(a))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(B)), new ArrayList<Symbol>(List.of(new Epsilon()))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(C)), new ArrayList<Symbol>(List.of(B, A))));
        productions.add(new Production(new ArrayList<Symbol>(List.of(D)), new ArrayList<Symbol>(List.of(a))));


        Grammar grammar = new Grammar(nonTerminals, terminals, productions, S);

        Grammar.classifyGrammar(grammar);
        System.out.println();
        grammar.displayGrammarType2Or3();

        //there are none
        //ConverterToCNF.removeSelfLoopProductions(grammar);
        //grammar.displayGrammarType2Or3();

        System.out.println("\t1. Removing epsilon productions:");
        ConverterToCNF.removeEpsilonProductions(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t2. Removing unit productions:");
        ConverterToCNF.removeUnitProductions(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t3. Removing inaccessible symbols:");
        ConverterToCNF.removeInaccessibleSymbols(grammar);
        grammar.displayGrammarType2Or3();

        System.out.println("\t4. Removing unproductive rules: (there are none in my variant)");
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
