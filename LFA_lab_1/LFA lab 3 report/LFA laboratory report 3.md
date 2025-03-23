# Laboratory Work No. 3

### Course: Formal Languages & Finite Automata
### Author: Țugui Artur
### Group: FAF-231

----

## Theory
Lexical analysis, or scanning, is the first phase of a compiler or interpreter. It converts a sequence of characters into a sequence of tokens, which are meaningful units of a programming language. A **lexer** (or tokenizer) takes raw input and breaks it into categorized tokens such as keywords, identifiers, operators, and literals.

A lexer operates based on formal grammar rules, typically defined using **regular expressions** or **finite automata**. The lexical analysis process ensures that the given input adheres to the expected structure before passing it to later stages like parsing.

## Objectives
1. Understand what lexical analysis is.
2. Get familiar with the inner workings of a lexer/scanner/tokenizer.
3. Implement a sample lexer and show how it works.

## Implementation Description

### Boolean Expression Lexer
The implemented lexer processes Boolean expressions consisting of logical operators (AND, OR, NOT, NAND, NOR, XOR, XNOR), parentheses, Boolean literals (`true`, `false`), and variables. The lexer operates as follows:

#### **Tokenization Process:**
- The lexer reads characters from the input and groups them into **tokens**.
- It recognizes keywords (`true`, `false`, `AND`, `OR`, `NOT`, `NAND`, `NOR`, `XOR`, `XNOR`) directly using a **prefix match**.
- It identifies integer values (`0`, `1`) while rejecting invalid numbers.
- Variables start with a letter and can contain alphanumeric characters and underscores (`_`).
- Special characters like `(` and `)` are treated as separate tokens.
- Whitespace is ignored.
- Unexpected characters throw an error.

### **Design Choices**
- **Peek and Advance:** The lexer maintains a `position` variable to traverse the input.
- **Match Function:** A helper function checks whether a keyword is at the current position.
- **Token Class:** Represents a token with a `type` and `lexeme`.
- **TokenType Enumeration:** For token type.
- **JUnit Tests:** Validates correct tokenization and error handling.

### **Recognized Tokens**
| Token Type  | Example  |
|------------|---------|
| TRUE       | `true`  |
| FALSE      | `false` |
| AND        | `AND`   |
| OR         | `OR`    |
| NOT        | `NOT`   |
| NAND       | `NAND`  |
| NOR        | `NOR`   |
| XOR        | `XOR`   |
| XNOR       | `XNOR`  |
| LPAREN     | `(`     |
| RPAREN     | `)`     |
| INTEGER    | `0`, `1`|
| VARIABLE   | `var1`, `flag_enabled` |
| EOF        | End of input |

### **Test Cases**
The lexer was tested using JUnit to verify correctness:
- **Boolean literals**: Successfully recognizes `true` and `false`.
- **Logical operators**: Correctly identifies AND, OR, NOT, NAND, NOR, XOR, and XNOR.
- **Parentheses handling**: Ensures `(` and `)` are correctly tokenized.
- **Integer values**: Accepts `0` and `1` while rejecting invalid numbers.
- **Variable recognition**: Allows letters, digits, and underscores but rejects names starting with `_`.
- **Error handling**: Detects unexpected characters like `@` and invalid numbers like `2`.

## Results
![Example input](LFA%20lab3%20input1.png)

## Conclusions
1. A lexer is essential for breaking raw input into structured tokens for further processing.
2. Implementing a Boolean expression lexer helped reinforce concepts related to tokenization, regular expressions, and automata.
3. A well-structured lexer can be easily extended to handle more complex expressions and rules.
4. The lexer is only responsible for tokenization, the validation of input is done by a parser, and the evaluation by an interpreter.
5. Automated testing ensures the lexer correctly handles valid and invalid inputs.

## References
- [A sample of a lexer implementation](https://llvm.org/docs/tutorial/MyFirstLanguageFrontend/LangImpl01.html)
- [About lexical analysis on Wikipedia](https://en.wikipedia.org/wiki/Lexical_analysis)
- Compiler Design: Principles, Techniques, and Tools – Aho, Sethi & Ullman  
