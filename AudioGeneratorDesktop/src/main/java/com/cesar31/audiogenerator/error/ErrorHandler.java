package com.cesar31.audiogenerator.error;

import com.cesar31.audiogenerator.parser.Token;
import java.util.ArrayList;
import java.util.List;
import com.cesar31.audiogenerator.error.Err.TypeErr;

/**
 *
 * @author cesar31
 */
public class ErrorHandler {

    private List<Err> errors;

    public ErrorHandler() {
        this.errors = new ArrayList<>();
    }

    public void setError(Token token, String type, List<String> expected) {
        Err.TypeErr kindE = type.equals("ERROR") ? TypeErr.LEXICO : TypeErr.SEMANTICO;
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

    public List<Err> getErrors() {
        return errors;
    }
}
