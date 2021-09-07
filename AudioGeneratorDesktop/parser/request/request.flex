package com.cesar31.audiogenerator.parser.request;

import static com.cesar31.audiogenerator.parser.request.RequestParserSym.*;
import java_cup.runtime.Symbol;
import java.util.Stack;

import com.cesar31.audiogenerator.parser.Token;

%%

%class RequestLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column

%{
	StringBuffer string = new StringBuffer();

	private Symbol symbol(int type) {
		return new Symbol(type, yyline + 1, yycolumn + 1, new Token(type, yyline + 1, yycolumn + 1));
	}

	private Symbol symbol(int type, Object object) {
		return new Symbol(type, yyline + 1, yycolumn + 1, new Token(type, (String) object, yyline + 1, yycolumn + 1));
	}
%}

%eofval{
	return symbol(EOF);
%eofval}
%eofclose

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* id */
Id = [a-zA-Z]\w*

/* Estados */
%state STRING

%%

<YYINITIAL> {
    "solicitud"
    { return symbol(REQUEST, yytext()); }

    "tipo"
    { return symbol(TYPE, yytext()); }

    "nombre"
    { return symbol(NAME, yytext()); }

    "Lista"
    { return symbol(LIST, yytext()); }

    "Pista"
    { return symbol(TRACK, yytext()); }

    {Id}
    { return symbol(ID, yytext()); }

	">"
	{ return symbol(GREATER, yytext()); }

	"<"
	{ return symbol(SMALLER, yytext()); }

    "/"
	{ return symbol(DIVIDE, yytext()); }

    \"
    {
		string.setLength(0);
		yybegin(STRING);
	}

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
