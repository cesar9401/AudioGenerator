package com.cesar31.audiogenerator.parser.playlist;

import static com.cesar31.audiogenerator.parser.playlist.PlaylistParserSym.*;
import java_cup.runtime.Symbol;

import com.cesar31.audiogenerator.parser.Token;

%%

%class PlaylistLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column
// %standalone
// %cupdebug

%{
	StringBuffer string = new StringBuffer();

	private Symbol symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn + 1, new Token(type, yyline + 1, yycolumn + 1));
		// return new Symbol(type, yyline + 1, yycolumn + 1);
	}

	private Symbol symbol(int type, Object object) {
		return new Symbol(type, yyline + 1, yycolumn + 1, new Token(type, (String) object, yyline + 1, yycolumn + 1));
		// return new Symbol(type, yyline + 1, yycolumn + 1, object);
	}
%}

%eofval{
	return symbol(EOF);
%eofval}
%eofclose

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* Id, nombre de pistas y playlists */
Id = [a-zA-Z]\w*

/* No match */
Sym = [\(\)`~!@#\$%\^&\*\-\+\|\\;'\./\?¡²³¤€¼½¾‘’¥×äå®þüö«»¬ßðø¶´æ©µç¿\w]+

/* Estados */
%state STRING

%%

<YYINITIAL> {
    "lista"
    { return symbol(LIST, yytext()); }

    "nombre"
    { return symbol(NAME, yytext()); }

    "random"
    { return symbol(RANDOM, yytext()); }

    "circular"
    { return symbol(CIRCULAR, yytext()); }

    "pistas"
    { return symbol(TRACKS, yytext()); }

    "true"
    { return symbol(TRUE, yytext()); }

    "false"
    { return symbol(FALSE, yytext()); }

    {Id}
    { return symbol(ID, yytext()); }

    ":"
    { return symbol(COLON, yytext()); }

    ","
    { return symbol(COMMA, yytext()); }

    "{"
    { return symbol(LBRACE, yytext()); }

    "}"
    { return symbol(RBRACE, yytext()); }

    "["
    { return symbol(LBRACKET, yytext()); }

    "]"
    { return symbol(RBRACKET, yytext()); }

    \"
    {
        string.setLength(0);
        yybegin(STRING);
    }

    {Sym}
    { return symbol(SYM, yytext()); }

    {WhiteSpace}
    { /* Ignore */ }
}

<STRING> {
    \"
    {
        yybegin(YYINITIAL);
        return symbol(STR, string.toString());
    }

    [^\n\r\"\\]+
    { string.append(yytext()); }

    \\t
    { string.append('\t'); }

    \\n
    { string.append('\n'); }

    \\r
    { string.append('\r'); }

    \\\"
    { string.append('\"'); }

    \\
    { string.append('\\'); }
}

[^]
{
	System.out.println("Error: < " + yytext() + " >");
	return symbol(ERROR, yytext());
}
