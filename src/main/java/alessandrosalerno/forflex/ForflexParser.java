package alessandrosalerno.forflex;

/*
Grammar system:
    [stuff] -> stuff is optional
    <stuff> -> stuff is a non-terminal
    stuff -> stuff is a terminal
    {stuff}* -> repeat stuff as many times as you want >= 0 (e.g., stuffstuffstuff)
    foo|bar -> either foo or bar
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
    <factor>     := -<factor>|(<expr>)|<number>|<identifier>|<call>
 */

public class ForflexParser {
}
