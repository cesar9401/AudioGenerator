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
        list = getListGrammar();
    }
    
    public void setError(Token token, String type, List<String> expected) {
        Err.TypeErr kindE = type.equals("ERROR") || type.equals("SYM") ? TypeErr.LEXICO : TypeErr.SEMANTICO;
        String lexema = type.equals("EOF") ? "Fin de entrada" : token.getValue();
        
        Err e = new Err(kindE, token.getLine(), token.getColumn(), lexema);
        String description = kindE == TypeErr.LEXICO ? "La cadena no se reconoce en el lenguaje." : "";
        description += "Se encontro " + lexema + ". Se esperaba: ";
        
        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals("error")) {
                description += "\'" + expected.get(i) + "\'";
                description += i == expected.size() - 1 ? "." : ",";
            }
        }
        
        e.setDescription(description);
        errors.add(e);
    }
    
    public void setErrorForListGram(Token token, String type, List<String> expected) {
        Err.TypeErr kindE = type.equals("ERROR") ? TypeErr.LEXICO : TypeErr.SEMANTICO;
        String lexema = type.equals("EOF") ? "Fin de entrada" : token.getValue();
        
        Err e = new Err(kindE, token.getLine(), token.getColumn(), lexema);
        String description = kindE == TypeErr.LEXICO ? "La cadena no se reconoce en el lenguaje." : "";
        description += "Se encontro " + lexema + ". Se esperaba: ";
        
        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals("error")) {
                description += "\'" + list.get(expected.get(i)) + "\'";
                description += i == expected.size() - 1 ? "." : ",";
            }
        }
        
        e.setDescription(description);
        errors.add(e);
    }
    
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
    
    public List<Err> getErrors() {
        return errors;
    }
}
