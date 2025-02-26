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
- Java functions callable from inside the expressions
- Support for strings in function parameters
- Support for expression parameters (passed from Java program)
- Support for custom algebric structures (Real numbers are the default)
- Custom exceptions and error messages

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
    <param>      := <expr>|<string>
    <params>     := [<param>{, <param>}*]
    <call>       := <identifier>(<params>)
    <term>       := <factor>[{*|/}<factor>]
    <factor>     := -<factor>|(<expr>)|<number>|<identifier>|<call>
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

## License
Forflex is distributed under the Apache License 2.0.
