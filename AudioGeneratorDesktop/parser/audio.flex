package com.cesar31.audiogenerator.parser;

import static com.cesar31.audiogenerator.parser.AudioParserSym.*;
import java_cup.runtime.Symbol;
import java.util.Stack;

%%

%class AudioLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
%line
%column
// %standalone
// %cupdebug

%{
	private Stack<Integer> stack = new Stack<>();
	private boolean end = false;

	StringBuffer string = new StringBuffer();
	StringBuffer character = new StringBuffer();

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
	// System.out.println("Stack -> " + stack.size());
	return symbol(EOF);
%eofval}
%eofclose

LineTerminator = \r|\n|\r\n
WhiteSpace = [ \t]+
Tab = \t

/* Coments */
InputCharacter = [^\r\n]
LineComment = [ \t]* ">>" {InputCharacter}*
CommentContent = ([^-]|\-+[^"->"]|\-*[\w<,:;\'\"])*
BlockComment = [ \t]* "<-" {CommentContent} "-"* "->"

Comment = {LineComment} | {BlockComment}

/* Integer and Decimal */
Integer = 0|[1-9][0-9]*
Decimal = {Integer} \. \d+

/* id for variables */
Id = [a-zA-Z]\w*

/* Estados */
%state STRING
%state CHARACTER
%state LINE

%%

<YYINITIAL, LINE> {
	<<EOF>>
	{
		if(!stack.isEmpty() && !end) {
			end = true;
			return symbol(EOL);
		} else {
			while(!stack.isEmpty()) {
				stack.pop();
				return symbol(DEDENT);
			}
		}

		return symbol(EOF);
	}

	{Comment}
	{ /* Ignore */ }

	// [\s]+\n?$
	// { /* Ignore */ }
}

<YYINITIAL> {

	"Pista"|"pista"
	{ return symbol(TRACK, yytext()); }

	"Extiende"|"extiende"
	{ return symbol(EXTENDS, yytext()); }

	"Keep"|"keep"
	{ return symbol(KEEP, yytext()); }

	"Var"|"var"
	{ return symbol(VAR, yytext()); }

	"entero"|"Entero"
	{ return symbol(INT, yytext()); }

	"doble"|"Doble"
	{ return symbol(DOB, yytext()); }

	"boolean"|"Boolean"
	{ return symbol(BOOL, yytext()); }

	"caracter"|"Caracter"
	{ return symbol(CAR, yytext()); }

	"cadena"|"Cadena"
	{ return symbol(CAD, yytext()); }

	"verdadero"|"Verdadero"
	{ return symbol(TRUE, yytext()); }

	"falso"|"Falso"
	{ return symbol(FALSE, yytext()); }

	"true"|"True"
	{ return symbol(TRUE, yytext()); }

	"false"|"False"
	{ return symbol(FALSE, yytext()); }

	"arreglo"|"Arreglo"
	{ return symbol(ARRAY, yytext()); }

	"si"|"Si"
	{ return symbol(IF, yytext()); }

	"sino"|"Sino"
	{ return symbol(ELSE, yytext()); }

	"switch"|"Switch"
	{ return symbol(SWITCH, yytext()); }

	"caso"|"Caso"
	{ return symbol(CASE, yytext()); }

	"salir"|"Salir"
	{ return symbol(EXIT, yytext()); }

	"default"|"Default"
	{ return symbol(DEFAULT, yytext()); }

	"para"|"Para"
	{ return symbol(FOR, yytext()); }

	"mientras"|"Mientras"
	{ return symbol(WHILE, yytext()); }

	"hacer"|"Hacer"
	{ return symbol(DO_WHILE, yytext()); }

	"continuar"|"Continuar"
	{ return symbol(CONTINUE, yytext()); }

	"retorna"|"Retorna"
	{ return symbol(RETURN, yytext()); }

	"reproducir"|"Reproducir"
	{ return symbol(PLAY, yytext()); }

	"do"|"Do"
	{ return symbol(DO, yytext()); }

	"do#"|"Do#"
	{ return symbol(DO_, yytext()); }

	"re"|"Re"
	{ return symbol(RE, yytext()); }

	"re#"|"Re#"
	{ return symbol(RE_, yytext()); }

	"mi"|"Mi"
	{ return symbol(MI, yytext()); }

	"fa"|"Fa"
	{ return symbol(FA, yytext()); }

	"fa#"|"Fa#"
	{ return symbol(FA_, yytext()); }

	"sol"|"Sol"
	{ return symbol(SOL, yytext()); }

	"sol#"|"Sol#"
	{ return symbol(SOL_, yytext()); }

	"la"|"La"
	{ return symbol(LA, yytext()); }

	"la#"|"La#"
	{ return symbol(LA_, yytext()); }

	/* Match con IF */
	// "si"|"Si"
	// { return symbol(SI, yytext()); }

	"espera"|"Espera"
	{ return symbol(WAIT, yytext()); }

	"ordenar"|"Ordenar"
	{ return symbol(ORDER, yytext()); }

	"ascendente"|"Ascendente"
	{ return symbol(ASC, yytext()); }

	"descendente"|"Descendente"
	{ return symbol(DESC, yytext()); }

	"pares"|"Pares"
	{ return symbol(EVEN, yytext()); }

	"impares"|"Impares"
	{ return symbol(ODD, yytext()); }

	"primos"|"Primos"
	{ return symbol(PRIME, yytext()); }

	"sumarizar"|"Sumarizar"
	{ return symbol(SUM, yytext()); }

	"longitud"|"Longitud"
	{ return symbol(LENGTH, yytext()); }

	"mensaje"|"Mensaje"
	{ return symbol(MSG, yytext()); }

	"principal"|"Principal"
	{ return symbol(MAIN, yytext()); }

	{Id}
	{ return symbol(ID, yytext()); }

	{Integer}
	{ return symbol(INTEGER, yytext()); }

	{Decimal}
	{ return symbol(DECIMAL, yytext()); }

	"=="
	{ return symbol(EQEQ, yytext()); }

	"!="
	{ return symbol(NEQ, yytext()); }

	">"
	{ return symbol(GREATER, yytext()); }

	"<"
	{ return symbol(SMALLER, yytext()); }

	">="
	{ return symbol(GRTREQ, yytext()); }

	"<="
	{ return symbol(SMLLREQ, yytext()); }

	"!"
	{ return symbol(NOT, yytext()); }

	"!!"
	{ return symbol(NULL, yytext()); }

	"&&"
	{ return symbol(AND, yytext()); }

	"!&&"
	{ return symbol(NAND, yytext()); }

	"||"
	{ return symbol(OR, yytext()); }

	"!||"
	{ return symbol(NOR, yytext()); }

	"&|"
	{ return symbol(XOR, yytext()); }

	","
	{ return symbol(COMMA, yytext()); }

	"="
	{ return symbol(EQUAL, yytext()); }

	"+"
	{ return symbol(PLUS, yytext()); }

	"-"
	{ return symbol(MINUS, yytext()); }

	"*"
	{ return symbol(TIMES, yytext()); }

	"/"
	{ return symbol(DIVIDE, yytext()); }

	"%"
	{ return symbol(MOD, yytext()); }

	"^"
	{ return symbol(POW, yytext()); }

	"+="
	{ return symbol(PLUS_EQ, yytext()); }

	"++"
	{ return symbol(PLUS_PLUS, yytext()); }

	"--"
	{ return symbol(MINUS_MINUS, yytext()); }

	"{"
	{ return symbol(LBRACE, yytext()); }

	"}"
	{ return symbol(RBRACE, yytext()); }

	"["
	{ return symbol(LBRACKET, yytext()); }

	"]"
	{ return symbol(RBRACKET, yytext()); }

	"("
	{ return symbol(LPAREN, yytext()); }

	")"
	{ return symbol(RPAREN, yytext()); }

	";"
	{ return symbol(SEMI, yytext()); }

	\"
	{
		string.setLength(0);
		yybegin(STRING);
	}

	\'
	{
		character.setLength(0);
		yybegin(CHARACTER);
	}

	{LineTerminator}
	{
		yybegin(LINE);
		return symbol(EOL);
	}

	{WhiteSpace}
	{ /* Ignore */ }
}

/* Estado para aceptar tabulaciones despues de un salta de linea */
<LINE> {
	\n
	{ return symbol(EOL); }

	({Tab}|" ")*\n
	{ return symbol(EOL); }

	({Tab}|"    ")+
	{
		int amount = yytext().replace("    ", "\t").length();
		// System.out.println("tab -> " + amount);

		if(stack.isEmpty()) {
			if(amount > 1) {
				// error identacion
				System.out.println("error indent " + amount + " -> " + (yyline + 1) + ", " + (yycolumn + 1));
				yybegin(YYINITIAL);
			} else {
				// generar token INDENT
				stack.push(amount);
				yybegin(YYINITIAL);
				return symbol(INDENT);
			}
		} else if(amount == stack.peek()) {
			// do nothing
			yybegin(YYINITIAL);
		} else if(amount == stack.peek() + 1) {
			// generar token INDENT
			stack.push(amount);
			yybegin(YYINITIAL);
			return symbol(INDENT);
		} else if(amount > stack.peek() + 1) {
			// error identacion
			System.out.println("error indent " + amount + " -> " + (yyline + 1) + ", " + (yycolumn + 1));
			yybegin(YYINITIAL);
		}else if(amount < stack.peek()) {
			while(amount != stack.peek()) {
				stack.pop();
				//push back
				yypushback(yytext().length());

				//emitir token DEDENT
				return symbol(DEDENT);
			}
			yybegin(YYINITIAL);
		}
	}

	[^ \r\n\t]+
	{
		if(!stack.isEmpty()) {
			System.out.println("Error ident -> nivel de indentacion actual: " + stack.peek() + " -> " + (yyline + 1) + ", " + (yycolumn + 1));
		}
		yypushback(yytext().length());
		yybegin(YYINITIAL);
	}

	{WhiteSpace}
	{/* Ignore */}
}

/* Estado para construir strings entre comilla doble */
<STRING> {
	\"
	{
		yybegin(YYINITIAL);
		return symbol(STR, string.toString());
	}

	[^\n\r\t\"\\#]+
	{ string.append(yytext()); }

	/* nuevos escapes con # */
	#t
	{ string.append('\t'); }

	#n
	{ string.append('\n'); }

	#r
	{ string.append('\r'); }

	#\"
	{ string.append('\"'); }

	##
	{ string.append('#'); }
	/* nuevos escapes con # */

	/* escapes con \ */
	\\
	{ string.append('\\'); }

	\t
	{ string.append("\\t"); }

	\n
	{ string.append("\\n"); }

	\r
	{ string.append("\\r"); }
	/* escapes con \ */
}

/* Estado para construir char entre comilla simple */
<CHARACTER> {
	\'
	{
		yybegin(YYINITIAL);
		return symbol(CHAR, character.toString());
	}

	[^\n\r\t\'\\#]{1,1}
	{
		character.append(yytext());
	}

	/* escapes o caracteres especiales con # */
	#t
	{ character.append('\t'); }

	#n
	{ character.append('\n'); }

	#r
	{ character.append('\r'); }

	#\'
	{ character.append('\''); }

	##
	{ character.append('#'); }
	/* escapes o caracteres especiales con # */

	/* caracter de escape \ */
	\\
	{ character.append('\\'); }
	/* caracter de escape \ */
}

[^]
{
	System.out.println("Error: < " + yytext() + " >");
	return symbol(ERROR, yytext());
}
