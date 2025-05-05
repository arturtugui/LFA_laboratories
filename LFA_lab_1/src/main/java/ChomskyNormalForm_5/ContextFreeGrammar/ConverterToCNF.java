package ChomskyNormalForm_5.ContextFreeGrammar;

import Grammar.Grammar;
import Grammar.Production;
import Grammar.symbols.Epsilon;
import Grammar.symbols.NonTerminal;
import Grammar.symbols.Symbol;
import Grammar.symbols.Terminal;

import java.util.*;

public class ConverterToCNF {
    // do not forget about S0 -> S
    public static int currentX;

    public static void removeSelfLoopProductions(Grammar grammar) {
        List<Production> filtered = new ArrayList<>();

        for(Production prod : grammar.productions){
            if(!Objects.equals(prod.left.toString(), prod.right.toString())){
                filtered.add(prod);
            }
        }

        grammar.productions = filtered;

        ConverterToCNF.recomputeSymbols(grammar);
    }

    public static void removeEpsilonProductions(Grammar grammar) {
        List<Production> filtered = new ArrayList<>();
        Set<Symbol> newNullables = new HashSet<>();
        Set<Symbol> oldNullables = new HashSet<>();

        // identifying null productions
        for(Production prod : grammar.productions){
            if(prod.isEpsilon()){
                newNullables.add(prod.left.getFirst());
            }
        }
        //System.out.println("epsilon productions: " + newNullables);

        // finding production containing nullables
        while(!newNullables.equals(oldNullables)){
            oldNullables = new HashSet<>(newNullables);

            for(Production prod : grammar.productions){
                if(consistsOnlyOf(prod.right, newNullables) && !newNullables.contains(prod.left.getFirst())){
                    newNullables.add(prod.left.getFirst());
                }
            }

            //System.out.println("new nullable: " + newNullables);
            //System.out.println("old nullable: " + oldNullables);
        }

        // remove the epsilon productions
        for(Production prod : grammar.productions){
            if(!prod.isEpsilon() && !consistsOnlyOf(prod.right, newNullables)){
                filtered.add(prod);
            }
        }

        // add combinations
        boolean changed = true;
        List<Boolean> trackVisitedProductions = new ArrayList<>(Collections.nCopies(filtered.size(), false));
        while(changed){
            changed = false;

            for(int j = 0; j < filtered.size(); j++){
                Production prod = filtered.get(j);
                //System.out.println("currently analyzed: " + prod);

                // counting nr. of nullables
                int numberOfNullables = (int) prod.right.stream()
                        .filter(newNullables::contains)
                        .count();

                // adding productions with one nullable removed
                if(numberOfNullables > 0 && !trackVisitedProductions.get(j)){
                    trackVisitedProductions.set(j, true);
                    for(int i = 0; i < numberOfNullables; i++){
                        int place = indexOfNthOccurrence(prod.right, newNullables, i+1);
                        List<Symbol> newRHS = new ArrayList<>(prod.right);
                        newRHS.remove(place);
                        Production newProd = new Production(prod.left, newRHS);
                        if (!filtered.contains(newProd)) { // careful, i overridden .equals()
                            filtered.add(newProd);
                            trackVisitedProductions.add(false);
                            //System.out.println("added: " + newProd);
                        }
                        changed = true;
                    }
                }
            }
            //System.out.println("\n");
        }

        // check the exception
        if(newNullables.contains(grammar.startSymbol)){
            filtered.add(new Production(List.of(grammar.startSymbol), List.of(new Epsilon())));
        }

        grammar.productions = filtered;

        ConverterToCNF.recomputeSymbols(grammar);
    }

    public static boolean consistsOnlyOf(List<Symbol> list, Set<Symbol> allowedSymbols) {
        for (Symbol symbol : list) {
            if (!allowedSymbols.contains(symbol)) {
                return false;
            }
        }
        return true;
    }

    public static int indexOfNthOccurrence(List<Symbol> list, Set<Symbol> targets, int n) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (targets.contains(list.get(i))) {
                count++;
                if (count == n) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void removeUnitProductions(Grammar grammar){
        List<Production> filtered = new ArrayList<>();
        List<Production> unitProductions = new ArrayList<>();
        List<Production> nonUnitProductions = new ArrayList<>();
        boolean hasUnit = false;

        //identify unit and non-unit productions
        //System.out.println("\tInitial unit productions:");
        for (Production prod : grammar.productions){
            if (prod.right.size() == 1 && prod.right.getFirst() instanceof NonTerminal){
                unitProductions.add(prod);
                hasUnit = true;
                //System.out.println(prod);
            }
            else {
                nonUnitProductions.add(prod);
            }
        }

        //while there are new unit prod
        while (hasUnit){
            hasUnit = false;

            for (Production unit : unitProductions){
                //System.out.println("Finding productions for " + unit);
                NonTerminal A = (NonTerminal) unit.left.getFirst();
                NonTerminal B = (NonTerminal) unit.right.getFirst();

                //add A->X to filtered, where A->B and B->X from productions
                for (Production prod : grammar.productions){
                    if (prod.left.getFirst().equals(B)){
                        Production newProd = new Production(List.of(A), prod.right);
                        filtered.add(new Production(List.of(A), prod.right));
                        //System.out.println("Added to filter" + newProd);
                    }
                }
            }

            //identify new unit and non-unit productions
            unitProductions = new ArrayList<>();

            for (Production prod : filtered){
                if (prod.right.size() == 1 && prod.right.getFirst() instanceof NonTerminal){
                    unitProductions.add(prod);
                    hasUnit = true;
                    //System.out.println("New unit prod found " + prod);
                }
                else {
                    //check if it is already not there
                    if(!nonUnitProductions.contains(prod)){
                        nonUnitProductions.add(prod);
                    }
                }
            }

            filtered = new ArrayList<>();
        }


        grammar.productions = nonUnitProductions;
        ConverterToCNF.recomputeSymbols(grammar);
    }

    public static void recomputeSymbols(Grammar grammar){
        Set<NonTerminal> existingNonTerminals = new HashSet<>();
        Set<Terminal> existingTerminals = new HashSet<>();

        for(Production prod : grammar.productions){
            for(Symbol symbol : prod.right){
                if(symbol instanceof NonTerminal){
                    existingNonTerminals.add((NonTerminal) symbol);
                } else if(symbol instanceof Terminal){
                    existingTerminals.add((Terminal) symbol);
                }
            }

            for(Symbol symbol : prod.left){
                if(symbol instanceof NonTerminal){
                    existingNonTerminals.add((NonTerminal) symbol);
                } else if(symbol instanceof Terminal){
                    existingTerminals.add((Terminal) symbol);
                }
            }
        }

        grammar.nonTerminals = existingNonTerminals;
        grammar.terminals = existingTerminals;
    }

    public static void removeInaccessibleSymbols(Grammar grammar){
        Set<Symbol> newAccessibleSymbols = new HashSet<>();
        Set<Symbol> oldAccessibleSymbols = new HashSet<>();
        Set<Symbol> inaccessibleSymbols = new HashSet<>();

        newAccessibleSymbols.add(grammar.startSymbol);
        //System.out.println(newAccessibleSymbols);

        while(!newAccessibleSymbols.equals(oldAccessibleSymbols)){
            oldAccessibleSymbols.addAll(newAccessibleSymbols);

            for(Production prod : grammar.productions){
                for(Symbol symbol : prod.left){
                   if(oldAccessibleSymbols.contains(symbol)){
                       prod.right.stream()
                               .filter(rightSymbol -> !(rightSymbol instanceof Epsilon))
                               .forEach(newAccessibleSymbols::add);
                   }
                }
            }

            //System.out.println(newAccessibleSymbols);
        }

        for(NonTerminal nonTerminal : grammar.nonTerminals){
            if(!newAccessibleSymbols.contains(nonTerminal)){
                inaccessibleSymbols.add(nonTerminal);
            }
        }

        for(Terminal terminal : grammar.terminals){
            if(!newAccessibleSymbols.contains(terminal)){
                inaccessibleSymbols.add(terminal);
            }
        }

        grammar.productions.removeIf(prod -> inaccessibleSymbols.contains(prod.left.getFirst()));

        ConverterToCNF.recomputeSymbols(grammar);
    }

    public static void removeUnproductiveSymbols(Grammar grammar){
        Set<Symbol> newProductiveSymbols = new HashSet<>();
        Set<Symbol> oldProductiveSymbols = new HashSet<>();
        Set<Symbol> unproductiveSymbols = new HashSet<>();

        for(Production prod : grammar.productions){
            if(prod.right.stream()
                    .allMatch(symbol -> symbol instanceof Terminal || symbol instanceof Epsilon)){
                newProductiveSymbols.add(prod.left.getFirst());
            }
        }

        //System.out.println(newProductiveSymbols);

        while(!newProductiveSymbols.equals(oldProductiveSymbols)){
            oldProductiveSymbols.addAll(newProductiveSymbols);

            for(Production prod : grammar.productions){
                if (prod.right.stream()
                        .allMatch(symbol -> symbol instanceof Terminal
                                || symbol instanceof Epsilon
                                || oldProductiveSymbols.contains(symbol))) {
                    newProductiveSymbols.add(prod.left.getFirst());
                }

            }

            //System.out.println(newProductiveSymbols);
        }

        if (!newProductiveSymbols.contains(grammar.startSymbol)) {
            throw new IllegalArgumentException("Start symbol is not productive. Grammar cannot derive any strings.");
        }

        for(NonTerminal nonTerminal : grammar.nonTerminals){
            if(!newProductiveSymbols.contains(nonTerminal)){
                unproductiveSymbols.add(nonTerminal);
            }
        }

        grammar.productions.removeIf(prod ->
                unproductiveSymbols.contains(prod.left.getFirst()) ||
                        prod.right.stream().anyMatch(unproductiveSymbols::contains)
        );


        ConverterToCNF.recomputeSymbols(grammar);
    }

    public static void eliminateProductionsWithNonsolitaryTerminals(Grammar grammar){
        List<Production> newProductions = new ArrayList<>();
        List<Production> updatedProductions = new ArrayList<>();

        Map<Terminal, NonTerminal> terminalToX = new HashMap<>();

        for(Production prod : grammar.productions){
            if(prod.right.size() > 1 && prod.right.stream().anyMatch(symbol -> symbol instanceof Terminal)){
                //System.out.println("Analyzing" + prod);
                List<Symbol> newRight = new ArrayList<>();

                for(Symbol symbol : prod.right){
                    if(symbol instanceof Terminal){
                        NonTerminal X;

                        if(terminalToX.containsKey(symbol)){
                            X = terminalToX.get(symbol);
                        } else {
                            X = new NonTerminal("X" + currentX++);
                            terminalToX.put((Terminal) symbol, X);
                            //System.out.println("Address-like ID: " + System.identityHashCode(symbol));
                            newProductions.add(new Production(List.of(X), List.of(symbol)));
                        }

                        newRight.add(X);
                    } else {
                        newRight.add(symbol);
                    }
                }
                updatedProductions.add(new Production(prod.left, newRight));

            } else {
                updatedProductions.add(prod);
            }
        }

        grammar.productions = updatedProductions;
        grammar.productions.addAll(newProductions);

        ConverterToCNF.recomputeSymbols(grammar);
    }

    public static void eliminateLargeRHS(Grammar grammar){
        List<Production> newProductions = new ArrayList<>();
        List<Production> updatedProductions = new ArrayList<>();

        Map<List<Symbol>, NonTerminal> nonTerminalsToX = new HashMap<>();



        for(Production prod : grammar.productions){
            int rightLength = prod.right.size();
            if(prod.right.stream().allMatch(symbol -> symbol instanceof NonTerminal) && rightLength > 2) {
                //System.out.println("\tAnalyzing " + prod);

                Production updProd = prod;
                while(rightLength != 2){
                    List<Symbol> newRight = new ArrayList<>();

                    for(int i = 0; i < rightLength - 2; i++){
                        newRight.add(updProd.right.get(i));
                        //System.out.println(prod.right.get(i));
                    }

                    NonTerminal X;
                    List<Symbol> lastTwo = List.of(updProd.right.get(rightLength - 2),  updProd.right.getLast());

                    if(nonTerminalsToX.containsKey(lastTwo)){
                        X = nonTerminalsToX.get(lastTwo);
                        //System.out.println("Previous production found " + X +  "-->" + lastTwo);
                    } else {
                        X = new NonTerminal("X" + currentX++);
                        nonTerminalsToX.put(lastTwo, X);
                        Production newProd = new Production(List.of(X), lastTwo);
                        newProductions.add(newProd);
                        //System.out.println("New production added " + newProd);
                    }

                    newRight.add(X);
                    updProd = new Production(prod.left, newRight);
                    //System.out.println("Current state of this production " + updProd);

                    rightLength = updProd.right.size();
                }

                updatedProductions.add(updProd);

            } else{
                updatedProductions.add(prod);
            }
        }

        grammar.productions = updatedProductions;
        grammar.productions.addAll(newProductions);

        ConverterToCNF.recomputeSymbols(grammar);
    }

    public static void checkForS0(Grammar grammar){
        boolean flag = false;
        NonTerminal startSymbol = grammar.startSymbol;

        Production newProd = null;
        for(Production prod : grammar.productions){
            if(prod.right.stream().anyMatch(startSymbol::equals)){
                NonTerminal S0 = new NonTerminal("S0");
                newProd = new Production(List.of(S0), List.of(startSymbol));
                grammar.startSymbol = S0;
                flag = true;
            }
        }

        if(!(newProd == null)){
            grammar.productions.add(newProd);
        }

        Production toRemoveProd = null;
        newProd = null;
        if(flag){
            for(Production prod : grammar.productions){
                if(prod.left.getFirst().equals(startSymbol) && prod.right.getFirst() instanceof Epsilon && prod.right.size() == 1){

                    toRemoveProd = prod;
                    newProd = new Production(List.of(grammar.startSymbol), List.of(new Epsilon()));
                }
            }
        }

        if(!(toRemoveProd == null)){
            grammar.productions.remove(toRemoveProd);
        }

        if(!(newProd == null)){
            grammar.productions.add(newProd);
        }
    }

    public static void convertToCNF(Grammar grammar){
        ConverterToCNF.removeSelfLoopProductions(grammar);

        ConverterToCNF.removeEpsilonProductions(grammar);

        ConverterToCNF.removeUnitProductions(grammar);

        ConverterToCNF.removeInaccessibleSymbols(grammar);

        ConverterToCNF.removeUnproductiveSymbols(grammar);

        ConverterToCNF.currentX = 1;
        ConverterToCNF.eliminateProductionsWithNonsolitaryTerminals(grammar);

        ConverterToCNF.eliminateLargeRHS(grammar);

        ConverterToCNF.checkForS0(grammar);
    }
}
