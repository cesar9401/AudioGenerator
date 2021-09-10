package com.cesar31.audiogenerator.error;

import com.cesar31.audiogenerator.parser.Token;
import java.util.ArrayList;
import java.util.List;
import com.cesar31.audiogenerator.error.Err.TypeErr;
import java.util.HashMap;

/**
 *
 * @author cesar31
 */
public class ErrorHandler {

    private List<Err> errors;
    private HashMap<String, String> list;

    public ErrorHandler() {
        this.errors = new ArrayList<>();
        this.list = new HashMap<>();
    }

    public void initMainGrammar() {
        this.list = getMainGrammar();
    }
    
    public void initGrammarOfRequests() {
        this.list = getGrammarOfRequests();
    }

    public void initGrammarOfPlaylists() {
        this.list = getListGrammar();
    }

    /**
     * Errores gramatica principal
     *
     * @param token
     * @param type
     * @param expected
     */
    public void setError(Token token, String type, List<String> expected) {
        Err.TypeErr kindE = type.equals("ERROR") || type.equals("SYM") ? TypeErr.LEXICO : TypeErr.SEMANTICO;
        String lexema = type.equals("EOF") ? "Fin de entrada" : token.getValue();

        Err e = new Err(kindE, token.getLine(), token.getColumn(), lexema);
        String description = kindE == TypeErr.LEXICO ? "La cadena no se reconoce en el lenguaje." : "";
        description += "Se encontro " + lexema + ". Se esperaba: ";

        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals("error")) {
                description += getValue(expected.get(i));
                description += i == expected.size() - 1 ? "." : ", ";
            }
        }

        e.setDescription(description);
        errors.add(e);
    }

    /**
     * Errores gramatica para playlists
     *
     * @param token
     * @param type
     * @param expected
     */
    public void setErrorForListGram(Token token, String type, List<String> expected) {
        Err.TypeErr kindE = type.equals("ERROR") || type.equals("SYM") ? TypeErr.LEXICO : TypeErr.SEMANTICO;
        String lexema = type.equals("EOF") ? "Fin de entrada" : token.getValue();

        Err e = new Err(kindE, token.getLine(), token.getColumn(), lexema);
        String description = kindE == TypeErr.LEXICO ? "La cadena no se reconoce en el lenguaje." : "";
        description += "Se encontro " + lexema + ". Se esperaba: ";

        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals("error")) {
                description += getValue(expected.get(i));
                description += i == expected.size() - 1 ? "." : ", ";
            }
        }

        e.setDescription(description);
        errors.add(e);
    }

    /**
     * Errores para gramatica de peticiones del cliente
     *
     * @param token
     * @param type
     * @param expected
     */
    public void setErrorForGramOfRequest(Token token, String type, List<String> expected) {
        Err.TypeErr kindE = type.equals("ERROR") || type.equals("SYM") ? TypeErr.LEXICO : TypeErr.SEMANTICO;
        String lexema = type.equals("EOF") ? "Fin de entrada" : token.getValue();

        Err e = new Err(kindE, token.getLine(), token.getColumn(), lexema);
        String description = kindE == TypeErr.LEXICO ? "La cadena no se reconoce en el lenguaje." : "";
        description += "Se encontro " + lexema + ". Se esperaba: ";

        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals("error")) {
                description += getValue(expected.get(i));
                description += i == expected.size() - 1 ? "." : ", ";
            }
        }

        e.setDescription(description);
        errors.add(e);
    }

    /**
     * Obtener valor de list
     *
     * @param key
     * @return
     */
    private String getValue(String key) {
        return list.get(key) != null ? list.get(key) : key;
    }

    private HashMap<String, String> getMainGrammar() {
        HashMap<String, String> map = new HashMap<>();

        map.put("TRACK", "pista");
        map.put("EXTENDS", "extiende");
        map.put("KEEP", "keep");
        map.put("VAR", "var");
        map.put("INT", "entero");
        map.put("DOB", "doble");
        map.put("BOOL", "boolean");
        map.put("CAR", "caracter");
        map.put("CAD", "cadena");
        map.put("TRUE", "verdadero, true");
        map.put("FALSE", "falso, false");
        map.put("ARRAY", "arreglo");
        map.put("IF", "si");
        map.put("ELSE", "sino");
        map.put("SWITCH", "switch");
        map.put("CASE", "caso");
        map.put("EXIT", "salir");
        map.put("DEFAULT", "default");
        map.put("FOR", "para");
        map.put("WHILE", "mientras");
        map.put("DO_WHILE", "hacer");
        map.put("CONTINUE", "continuar");
        map.put("RETURN", "retorna");
        map.put("PLAY", "reproducir");
        map.put("NOTE", "do, do#, re, re#, mi, fa, fa#, sol, sol#, la, la#");
        map.put("WAIT", "espera");
        map.put("ORDER", "ordenar");
        map.put("WAY", "ascendente, descendente, pares, impares, primos");
        map.put("SUM", "sumarizar");
        map.put("LENGTH", "longitud");
        map.put("MSG", "mensaje");
        map.put("MAIN", "principal");
        map.put("ID", "identificador con caracteres alfanumericos");
        map.put("INTEGER", "numero entero");
        map.put("DECIMAL", "numero con coma flotante");
        map.put("EQEQ", "igual-igual (==)");
        map.put("NEQ", "no-igual (!=)");
        map.put("GREATER", "mayor que (>)");
        map.put("SMALLER", "menor que (<)");
        map.put("GRTREQ", "mayor o igual que (>=)");
        map.put("SMLLREQ", "menor o igual que (<=)");
        map.put("NOT", "negacion logica (!)");
        map.put("NULL", "operador nulo(!!)");

        map.put("AND", "operador logico and (&&)");
        map.put("NAND", "operador logico nand (!&&)");
        map.put("OR", "operador logico or (||)");
        map.put("NOR", "operador logico nor (!||)");
        map.put("XOR", "operador logico xor (&|)");
        map.put("COMMA", "coma (,)");
        map.put("EQUAL", "signo igual (=)");
        map.put("PLUS", "signo mas (+)");
        map.put("MINUS", "signo menos (-)");
        map.put("TIMES", "sigo de multiplicacion (*)");
        map.put("DIVIDE", "signo de division (/)");
        map.put("MOD", "signo modulo (%)");
        map.put("POW", "signo de potencia (^)");
        map.put("PLUS_EQ", "operador de asignacion (+=)");
        map.put("PLUS_PLUS", "operador de asignacion (++)");
        map.put("MINUS_MINUS", "operador de asignacion (--)");
        map.put("LBRACE", "llave de apertura ({)");
        map.put("RBRACE", "llave de cierre (})");
        map.put("LBRACKET", "corchete de apertura ([)");
        map.put("RBRACKET", "corchete de cierre (])");
        map.put("LPAREN", "parentesis de apertura `(`");
        map.put("RPAREN", "parentesis de cierre `)`");
        map.put("SEMI", "punto y coma (;)");
        map.put("EOL", "fin de linea");
        map.put("INDENT", "identacion");
        map.put("DEDENT", "nivel de indentacion menos");
        map.put("STR", "string entre comillas dobles");
        map.put("CHAR", "caracter entre comillas simples");
        map.put("EOF", "fin de entrada");

        return map;
    }

    /**
     * Para gramatica de playlists
     *
     * @return
     */
    private HashMap<String, String> getListGrammar() {
        HashMap<String, String> map = new HashMap<>();
        map.put("LIST", "lista");
        map.put("NAME", "nombre");
        map.put("RANDOM", "random");
        map.put("CIRCULAR", "circular");
        map.put("TRACKS", "pistas");
        map.put("TRUE", "true");
        map.put("FALSE", "falso");
        map.put("ID", "identificador(caracteres alfanumericos)");
        map.put("COLON", "dos puntos `:`");
        map.put("COMMA", "coma `,`");
        map.put("LBRACE", "llave izquierda `{`");
        map.put("RBRACE", "llave derecha `}`");
        map.put("LBRACKET", "corchete izquida `[`");
        map.put("RBRACKET", "corchete derecha `]`");
        map.put("STR", "string entre comillas");
        return map;
    }

    /**
     * Para gramatica de peticiones del cliente
     *
     * @return
     */
    private HashMap<String, String> getGrammarOfRequests() {
        HashMap<String, String> map = new HashMap<>();
        map.put("REQUEST", "solicitud");
        map.put("TYPE", "tipo");
        map.put("NAME", "nombre");
        map.put("LIST", "Lista");
        map.put("TRACK", "Pista");
        map.put("ID", "identificador(caracteres alfanumericos)");
        map.put("GREATER", "mayor que `>`");
        map.put("SMALLER", "menor que `<`");
        map.put("DIVIDE", "diagonal `/`");
        map.put("STR", "string entre comillas");
        return map;
    }

    public List<Err> getErrors() {
        return errors;
    }
}
