package com.cesar31.audiogeneratorclient.parser;

import static com.cesar31.audiogeneratorclient.parser.ClientParserSym.*;
import java_cup.runtime.Symbol;

%%

%class ClientLex
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

/* Integer and Decimal */
Integer = 0|[1-9][0-9]*
Decimal = {Integer} \. \d+

/* Estados */
%state STRING

%%

<YYINITIAL> {
    "errores"
    { return symbol(ERRORS, yytext()); }

    "error"
    { return symbol(ERROR, yytext()); }

    "tipo"
    { return symbol(TYPE, yytext()); }

    "LEXICO"
    { return symbol(TYPE_E, yytext()); }

    "SEMANTICO"
    { return symbol(TYPE_E, yytext()); }

    "SINTACTICO"
    { return symbol(TYPE_E, yytext()); }

    "EJECUCION"
    { return symbol(TYPE_E, yytext()); }

    "linea"
    { return symbol(LINE, yytext()); }

    "columna"
    { return symbol(COLUMN, yytext()); }

    "lexema"
    { return symbol(LEXEMA, yytext()); }

    "descripcion"
    { return symbol(DESCRIPTION, yytext()); }

    "pistas"
    { return symbol(TRACKS, yytext()); }

    "pista"
    { return symbol(TRACK, yytext()); }

    "nombre"
    { return symbol(NAME, yytext()); }

    "duracion"
    { return symbol(DURATION, yytext()); }

    "listas"
    { return symbol(LISTS, yytext()); }

    "lista"
    { return symbol(LIST, yytext()); }

    "random"
    { return symbol(RANDOM, yytext()); }

    "circular"
    { return symbol(CIRCULAR, yytext()); }

    "true"
    { return symbol(TRUE, yytext()); }

    "false"
    { return symbol(FALSE, yytext()); }

    "canal"
    { return symbol(CHANNEL, yytext()); }

    "numero"
    { return symbol(NUMBER, yytext()); }

    "nota"
    { return symbol(NOTE, yytext()); }

    "octava"
    { return symbol(EIGHTH, yytext()); }

    "="
    { return symbol(EQUAL, yytext()); }

    "<"
    { return symbol(SMALLER, yytext()); }

    ">"
    { return symbol(GREATER, yytext()); }

    "/"
    { return symbol(DIVIDE, yytext()); }

    {Integer}
    { return symbol(INTEGER, yytext()); }

    {Decimal}
    { return symbol(DECIMAL, yytext()); }

    \"
    { string.setLength(0); yybegin(STRING); }


    {WhiteSpace}
    { /* Ignore */ }
}

<STRING> {
    \"
    { yybegin(YYINITIAL); return symbol(STR, string.toString()); }

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
	return symbol(UNKNOWN, yytext());
}