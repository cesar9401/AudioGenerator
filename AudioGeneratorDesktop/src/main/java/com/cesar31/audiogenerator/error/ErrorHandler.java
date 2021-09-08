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
                description += expected.get(i);
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
