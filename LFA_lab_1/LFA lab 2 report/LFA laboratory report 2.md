# Laboratory Work No. 2

### Course: Formal Languages & Finite Automata
### Author: Artur Tugui
### Group: FAF-231

----

## Theory
A finite automaton (FA) is a 5-tuple (Q, Σ, δ, q0, F), where:
- Q is a finite set of states;
- Σ is an alphabet;
- δ: Q × S → Q is a transition function;
- q0 is the initial state and belongs to Q;
- F is a set of accepting states (or final states) and a subset of Q.

A Deterministic FA (DFA) has exactly one transition per state for each input symbol. A Non-deterministic FA (NFA) allows multiple transitions for the same input or none at all. An ε-NFA extends this by including ε-transitions, which allow state changes without consuming input. Despite differences, all three recognize the same class of languages.

DFAs, NFAs, and ε-NFAs are equivalent in power, as every regular language can be recognized by a finite automaton. Kleene’s theorem states that a language is regular if and only if it can be represented by a regular expression or a finite automaton.



##  Objectives:

1. Understand what an automaton is and what it can be used for.

2. Continuing the work in the same repository and the same project, the following need to be added: 

    a. Provide a function in your grammar type/class that could classify the grammar based on Chomsky hierarchy.

    b. For this you can use the variant from the previous lab.

3. According to your variant number (by universal convention it is register ID), get the finite automaton definition and do the following tasks:

    a. Implement conversion of a finite automaton to a regular grammar.

    b. Determine whether your FA is deterministic or non-deterministic.

    c. Implement some functionality that would convert an NDFA to a DFA.

    d. Represent the finite automaton graphically (Optional, and can be considered as a bonus point):

You can use external libraries, tools or APIs to generate the figures/diagrams.

Your program needs to gather and send the data about the automaton and the lib/tool/API return the visual representation.



## Implementation description

**Chomsky Classification of Grammars**

Some changes were necessary to the **Grammar** classes, to allow them to work with grammars of type 0 and type 1, the changes are listed below

**Refactoring related to grammar class**

- `Epsilon`: A new subclass of `Symbol` was added to represent empty string.
- `Production`: The left attribute's type was changed from `NonTerminal` to `Set<NonTerminal>`.
- `Grammar`: It was necessary to make some changes, namely how the function recursivelyGenerateString() works and other small things

With these changes, the class `Grammar` now supported all types of grammar, and I was able to write the function to classify the grammar

**Grammar classification steps**

1. The function iterates through all the grammar's production rules and inspects each one.

2. If the RHS of a production contains an epsilon (empty string), the grammar cannot be Type 1, but it might be Type 0, 2, or 3.

3. If the LHS contains an epsilon, the grammar is invalid.

4. If the LHS has more than one symbol, the grammar might be Type 0 or Type 1, but it can't be Type 2 or Type 3.

5. If the LHS has only one symbol, the RHS is further analyzed to classify the grammar.

6. If there are more than one non-terminal in the RHS, it could be Type 2.

7. If the RHS has no non-terminals, the function continues checking other rules.

8. If there’s a non-terminal, the function checks if it appears on the leftmost or rightmost side of the RHS to classify it as Type 3.


**Finite Automaton conversion to Regular Language**

As per Kleene - *a language is regular if and only if it can be represented by a regular expression or a finite automaton*, thus to perform this conversion the following was necessary:

- the `states` became `nonTerminals`;
- the `alphabet` became `terminals`;
- the `initialState` became `startSymbol`;
- the `transitions` have been converted to `productions`;
- and a new production from `finalState` to epsilon (empty string) was added.

**Determining whether a FA is deterministic or not**

A finite automaton is not a deterministic FA (DFA) when:
- it allows multiple transitions for the same input or none at all - nondeterministic FA (NFA). 
- it includes ε-transitions, which allow state changes without consuming input - ε-NFA.

**NFA to DFA conversion**


1. Initialization
    - A `dfaStates` map is created to store the DFA states, where each key is a set of NFA states and the corresponding value is a single DFA state.
    - A `dfaTransitions` list is created to store the transitions for the DFA.
    - A queue is initialized to manage the sets of NFA states to process.

2. Adding the initial state
    - The initial set of NFA states is created by adding the NFA's initial state to a set.
    - This initial set is added to the queue, and a new DFA state (`Q0`) is created and added to the `dfaStates` map.
    - A counter (`stateCounter`) is used to uniquely name the DFA states.

3. Process each state in the queue
    - While there are states in the queue, the algorithm:
        - Dequeues a set of NFA states (`currentSet`).
        - For each terminal symbol in the alphabet:
            - A new set of NFA states (`newStateSet`) is created by finding all reachable states from the `currentSet` on the given terminal symbol.
            - If `newStateSet` is not empty:
                - If the set of states has not been assigned a DFA state yet, a new DFA state is created and added to the `dfaStates` map. The new state set is also added to the queue.
                - A transition is added to the `dfaTransitions` list, linking the current DFA state to the new state using the terminal symbol.

4. Identifying the DFA's final states
    - A set of DFA final states is created by iterating over the sets of NFA states in `dfaStates` and checking if the set contains the NFA's final state. If it does, the corresponding DFA state is added to the set of DFA final states.



## Results
![The Finite Automaton](LFA%20lab2.1.png)

The program displays the given FA, and then the Regular Grammar obtained from it.

![The Regular Grammar](LFA%20lab2.2.png)

It displays that the FA is not deterministic, converts it to a DFA and displays it.

![The obtained DFA through analytical method in Java](LFA%20lab2.3.png)

The same result can be obtained through the tabular method

![Tabular method](Tabular%20method.jpg)
## Conclusions

1. A Regular grammar can be obtained from a FA, regardless if it is deterministic or not.

2. By creating a well-structured foundation, future refactoring can be avoided or minimized, unfortunately my initial design was not very wise.

3. After performing this laboratory, I can easily distinguish each type of grammar and have learned what it requires for one.

4. While the tabular method for converting a NFA to a DFA is the easiest, the analytical is the one which can be programmed.

5. This laboratory has reinforced my knowledge about Grammar, Finite Automaton, Conversion of FA and Regular Language.

## References

UTM LFA course, univ. lect. Irina Cojuhari, Theme 1, https://else.fcim.utm.md/mod/resource/view.php?id=41256
Theme 2, https://else.fcim.utm.md/mod/url/view.php?id=667
