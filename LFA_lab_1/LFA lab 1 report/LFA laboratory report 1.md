# Laboratory Work No. 1

### Course: Formal Languages & Finite Automata
### Author: Artur Tugui
### Group: FAF-231

----

## Theory
A grammar G is an ordered quadruple G = (VN, VT, P, S), where: 
- VN is a finite set of non-terminal symbols; 
- VT is a finite set of terminal symbols;
- S is a start symbol and belongs to VN;
- P is a finite set of productions  of rules.

An automaton is a mathematical model of a finite-state machine. The state machine is a machine, which is having a set of input symbols, is jumping through the set of states, based on the transition functions.

A deterministic finite automaton (DFA) is a 5-tuple (Q, Σ, δ, q0, F), where:
- Q is a finite set of states;
- Σ is an alphabet;
- δ: Q × S → Q is a transition function;
- q0 is the initial state and belongs to Q;
- F is a set of accepting states (or final states) and a subset of Q.

##  Objectives:

1. Discover what a language is and what it needs to have in order to be considered a formal one;

2. Provide the initial setup for the evolving project that you will work on during this semester. You can deal with each laboratory work as a separate task or project to demonstrate your understanding of the given themes, but you also can deal with labs as stages of making your own big solution, your own project. Do the following:

   a. Create GitHub repository to deal with storing and updating your project;

   b. Choose a programming language. Pick one that will be easiest for dealing with your tasks, you need to learn how to solve the problem itself, not everything around the problem (like setting up the project, launching it correctly and etc.);

   c. Store reports separately in a way to make verification of your work simpler (duh)

3. According to your variant number, get the grammar definition and do the following:

   a. Implement a type/class for your grammar;

   b. Add one function that would generate 5 valid strings from the language expressed by your given grammar;

   c. Implement some functionality that would convert and object of type Grammar to one of type Finite Automaton;

   d. For the Finite Automaton, please add a method that checks if an input string can be obtained via the state transition from it;


## Implementation description

**Grammar**

The **Grammar** structure, consisting of **Symbols**, **Terminals**, **NonTerminals**, and **Productions** to model a formal grammar, which can generate random strings based on defined production rules.

**Grammar Class Descriptions**

- `Symbol`: A base class representing a symbol, which can be either a terminal or non-terminal.
- `Terminal`: A subclass of `Symbol` representing terminal symbols in the grammar, which cannot be further expanded.
- `NonTerminal`: A subclass of `Symbol` representing non-terminal symbols, which can be expanded using production rules.
- `Production`: Represents a production rule in the grammar, consisting of a left-hand side (non-terminal) and a right-hand side (a list of symbols).
- `Grammar`: The main class that models a grammar, containing sets of terminals, non-terminals, and production rules, and it provides methods to generate random strings and convert the grammar into a finite automaton.

**Grammar Attributes**

The `Grammar` class contains the following attributes:
- `nonTerminals`: A set of `NonTerminal` symbols in the grammar.
- `terminals`: A set of `Terminal` symbols in the grammar.
- `productions`: A list of `Production` objects that define the grammar's rules.
- `startSymbol`: The starting non-terminal symbol from which string generation begins.

```java
public class Grammar {
    public Set<NonTerminal> nonTerminals;
    public Set<Terminal> terminals;
    public List<Production> productions;
    public NonTerminal startSymbol;
    //...
}
```

**String generation**

The `Grammar` class generates strings through the following steps:

1. Initialization:
    - Start with the `startSymbol` as the initial string.

2. String Generation:
    - Find a non-terminal symbol in the current string.
    - For the found non-terminal, randomly select a production rule where the left side matches the non-terminal.
    - Replace the non-terminal with the right-hand side of the selected production rule.
    - Repeat the process for newly replaced non-terminals.

3. Termination:
    - The string generation stops when no non-terminals remain in the string, and the string consists only of terminal symbols.


```pseudo
function generateString():
    Initialize string with startSymbol
    while string contains non-terminal:
        Find a non-terminal in the string
        Get all possible production rules for the non-terminal
        Randomly choose one production rule
        Replace the non-terminal with the right-hand side of the chosen production
    return the string
```

**Finite Automaton**

The **Finite Automaton** structure, consisting of **States**, **Transitions**, and an **Alphabet** to model a finite state machine that processes strings based on a set of transitions.

**Finite Automaton Class Descriptions**

- `Transition`: Represents a transition in the finite automaton, consisting of a state to transition from, a symbol that triggers the transition, and the state to transition to.
- `FiniteAutomaton`: The main class that models a finite automaton, containing a set of states, an alphabet of terminal symbols, a list of transitions, and a starting and final state. It also provides methods to check whether a string belongs to the language and to display the finite automaton.

**Finite Automaton Attributes**

The `FiniteAutomaton` class contains the following attributes:
- `states`: A set of `NonTerminal` states in the finite automaton.
- `alphabet`: A set of `Terminal` symbols that the automaton recognizes.
- `transitions`: A list of `Transition` objects that define the state transitions based on input symbols.
- `initialState`: The initial state from which the automaton starts processing input.
- `finalState`: The final state that indicates acceptance of the input string.

```java
public class FiniteAutomaton {
    public Set<NonTerminal> states;
    public Set<Terminal> alphabet;
    public List<Transition> transitions;
    public NonTerminal initialState;
    public NonTerminal finalState;
    //...
}
```
**Grammar to Finite Automaton**

The `toFiniteAutomaton()` function converts the grammar into a finite automaton by generating transitions for each production and creating a new automaton.

- `generateTransitions()` creates transitions by mapping non-terminal and terminal symbols from each production to the finite automaton's states.
- An additional state `final` was added to meet the ending requirements.
- The resulting transitions, along with non-terminals, terminals, and the start symbol, are used to build the finite automaton.

**Strings obtained through Finite Automaton**

The `FiniteAutomaton` class checks if a given string belongs to the language accepted by the automaton using the following process:

1. **Initialization**:
    - Start at the `initialState` of the finite automaton.

2. **String Processing**:
    - For each character in the input string, check if there is a transition from the current state triggered by that character.
    - If a valid transition is found, update the current state to the state specified by the transition.
    - If no valid transition is found, the string is rejected.

3. **Termination**:
    - After processing the entire string, if the current state matches the `finalState`, the string is accepted as belonging to the language.

```pseudo
function stringBelongToLanguage(inputString):
    Initialize currentState with initialState
    for each character ch in inputString:
        Find a transition from currentState that matches ch
        if no valid transition is found:
            return false
        else:
            Update currentState to the state in the transition
    if currentState is finalState:
        return true
    return false
```

## Results
![The Finite Automaton](LFA%20lab1.drawio.png)

The program displays the strings generated following the Grammar rules, showing how they were made step-by-step and checks if the given strings could be created using the Finite Automaton.

![The output of the program](LFA%20lab1%20sc1.png)

## Conclusions

1. A grammar can be converted into a finite automaton, allowing automaton techniques to analyze generated strings.

2. String generation in a grammar replaces non-terminals with terminals until a valid string is formed.

3. The finite automaton checks if a string belongs to a language by simulating state transitions based on input symbols.

4. The implementation demonstrates the conversion of grammar to finite automaton and verification of generated strings.

5. Mapping production rules to automaton states connects formal language theory with finite automata, enabling efficient string verification.

## References

UTM LFA course, univ. lect. Irina Cojuhari, Theme 1, https://else.fcim.utm.md/mod/resource/view.php?id=41256
Theme 2, https://else.fcim.utm.md/mod/url/view.php?id=667