<p align="center">
    <h1 align="center">Forflex</h1>
    <p align="center"> Simple, flexible, and extensible expression parseing and evaluation library for Java </p>
</p>

<div align="center">

[contributors-shield]: https://img.shields.io/github/contributors/Alessandro-Salerno/Forflex.svg?style=flat-square
[contributors-url]: https://github.com/Alessandro-Salerno/Forflex/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Alessandro-Salerno/Forflex.svg?style=flat-square
[forks-url]: https://github.com/Alessandro-Salerno/Forflex/network/members
[stars-shield]: https://img.shields.io/github/stars/Alessandro-Salerno/Forflex.svg?style=flat-square
[stars-url]: https://github.com/Alessandro-Salerno/Forflex/stargazers
[issues-shield]: https://img.shields.io/github/issues/Alessandro-Salerno/Forflex.svg?style=flat-square
[issues-url]: https://github.com/Alessandro-Salerno/Forflex/issues
[license-shield]: https://img.shields.io/github/license/Alessandro-Salerno/Forflex.svg?style=flat-square
[license-url]: https://github.com/Alessandro-Salerno/Forflex/blob/master/LICENSE.txt

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
![](https://tokei.rs/b1/github/Alessandro-Salerno/Forflex)

</div>

## Features
- Java function invocation inside expressions
- Expression parameters
- Real and string algebras included
- Custom algebric structures other than real numbers
- Parse tree caching
- Error handling
- Can be used in both FOSS and proprietary software

## Syntax and grammar
The grammar is defined in pseudo-BNF as follows:
```
Grammar:
    <symbol>     := anything in UTF-8
    <digit>      := 0|1|2|3|4|5|6|7|8|9
    <integer>    := <digit>{<digit>}*
    <num>        := <integer>[.<integer>]
    <lowercase>  := a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z
    <uppercase>  := A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z
    <letter>     := <lowercase>|<uppercase>
    <string>     := "{<symbol>}*"
    <identifier> := <letter>|_{<letter>|<digit>|_}*
    <expr>       := <term>[{+|-}<term>]
    <params>     := [<expr>{, <expr>}*]
    <call>       := <identifier>(<params>)
    <term>       := <factor>[{*|/}<factor>]
    <factor>     := -<factor>|(<expr>)|<number>|<identifier>|<call>|<string>
```

For example, this is a valid statement:
```
round((a + b + c - d) / (5.47 * sin(1)) + priceof("AAPL", "2025-02-26"))
```
However, the round functions and the other symbols must be defied by the Java program, otherwise an exception is thrown:
```
1:1 ERROR: Undeclared identifier "round"
 1 | round((a + b + c - d) / (5.47 * sin(1)) + priceof("AAPL", "2025-02-26"))
   | ~~~~~
```

## Structure
Forflex has six main concepts:
- Algebric structures
- Evaluables
- Expressions
- Functions
- Parameters
- Parser

### Algebric structures
Algebric structures in Forflex define what operations can be applied to a number and how these operations behave. Algebric structures are classes that implement `ForflexAlgebra` and define methods for addition, subtraction, multiplication, and division. If a given algebric structure does not support one or more of these operations, it can throw a `ForflexUnsupportedOperationError`. The default algebric structure is `ForflexRealNumber`.

### Evaluables
Evaluables in Forflex are essentially nodes in the parse tree. Evaluables implement the `ForflexEvaluable` interface and its method `evaluate` which returns an instance of a `ForflexAlgebra` when called.
Forflex ships with four main evaluables:
- Identity (`ForflexIdentityNode` - returns the same instance of `ForflexAlgebra` that was passed)
- Binary (`ForflexBinaryNode` - holds two other evaluables on the left and right in order to make the binary tree)
- Function call (`ForflexFunctionCallNode` - used to represent a function call in the tree)
- Parameter (`ForflexParameterNode` - used to store references to parameters)

### Expressions
Expressions in Forflex are a special type of evaluable which stands outside the tree and is used to cahce the tree itself in order to reuse it. This removes the need to reparse the expression every time it has to be evaluated with a given set of parameters.

### Functions
Functions in Forflex are implementations of the `ForflexFunction` interface which, once added to a Parser, can be invoked from within an expression.

### Parameters
Parameters in Forflex are named values that reside in the host Java program but can be access in read-only mode within expressions.

### Parser
The Parser (`ForflexParser`) is the component responsible for constructing the tree. It first tokenizes the expression using the Lexer and then recursively scrolls the token list to build the tree.

## Example

```java
import alessandrosalerno.forflex.ForflexExpression;
import alessandrosalerno.forflex.ForflexFunction;
import alessandrosalerno.forflex.ForflexParser;
import alessandrosalerno.forflex.ForflexUtils;
import alessandrosalerno.forflex.errors.preprocessor.ForflexPreprocessorError;
import alessandrosalerno.forflex.errors.runtime.ForflexParameterCountError;
import alessandrosalerno.forflex.errors.runtime.ForflexParameterTypeError;
import alessandrosalerno.forflex.algebra.ForflexAlgebra;
import alessandrosalerno.forflex.algebra.ForflexRealNumber;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            String formula = "round((a + b + c - d) / (5.47 * sin(1)) + priceof(\"AAPL\", \"2025-02-26\"))";
            Map<String, ForflexAlgebra<?>> parameters = new HashMap<>();

            parameters.put("a", new ForflexRealNumber(1));
            parameters.put("b", new ForflexRealNumber(2));
            parameters.put("c", new ForflexRealNumber(3));
            parameters.put("d", new ForflexRealNumber(4));

            ForflexParser parser = new ForflexParser().addFunctions(ForflexUtils.DEFAULT_FUNCTIONS)
                                                        .addFunction("priceof", new ForflexFunction<ForflexRealNumber>() {
                @Override
                public ForflexRealNumber run(ForflexAlgebra<?>[] params) {
                    String symbol = ForflexUtils.requireParameterPrimitiveType(params, 0, String.class);
                    String date = ForflexUtils.requireParameterPrimitiveType(params, 1, String.class);
                    // Do some magic stock market stuff
                    return new ForflexRealNumber(200.5);
                }
            });

            ForflexExpression expr = parser.parse(formula, parameters);
            // Now the expression has been parsed, parameters can be changed at any time
            // NOTE: this means that parameters are NOT thread-safe!
            ForflexRealNumber result = (ForflexRealNumber) expr.evaluate();
            System.out.println(result.getPrimitive());
        } catch (ForflexPreprocessorError e) {
            e.printErrorMessage();
        } catch (ForflexParameterCountError
                 | ForflexParameterTypeError e) {
            e.printStackTrace();
        }
    }
}
```

## Installing Forflex with Maven
After downloading the JAR and placing it in some project directory (e.g., resources), use the following dependency structure in your pom.xml file:
```xml
<dependency>
    <groupId>alessandrosalerno.forflex</groupId>
    <artifactId>forflex</artifactId>
    <version>1.0.1</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/resources/forflex-1.0.1.jar</systemPath>
</dependency>
```
Alternatively, you can install the package from GitHub Packages:
```xml
<dependency>
    <groupId>alessandrosalerno.forflex</groupId>
    <artifactId>forflex</artifactId>
    <version>1.0.1</version>
</dependency>
```

## License
Forflex is distributed under the Apache License 2.0.

## Credits
- https://github.com/javalc6/Simple-Expression-Parser
