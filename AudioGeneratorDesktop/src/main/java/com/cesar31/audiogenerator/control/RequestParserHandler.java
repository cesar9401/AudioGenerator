package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import com.cesar31.audiogenerator.parser.request.RequestLex;
import com.cesar31.audiogenerator.parser.request.RequestParser;
import com.cesar31.audiogenerator.request.Request;
import com.cesar31.audiogenerator.request.RequestName;
import com.cesar31.audiogenerator.request.Type;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author csart
 */
public class RequestParserHandler {

    private FileControl file;

    public RequestParserHandler() {
        this.file = new FileControl();
    }

    public void parseSource(String data) {
        RequestLex lex = new RequestLex(new StringReader(data));
        RequestParser parser = new RequestParser(lex);
        try {
            List<Request> ast = (List<Request>) parser.parse().value;
            Token info = parser.getInfo();
            List<Err> errors = parser.getErrors();

            if (!errors.isEmpty()) {
                errors.forEach(System.out::println);
            } else {
                errors = new ArrayList<>();

                // Revisar errores sintacticos aqui
                HashMap<String, Request> map = checkAst(info, ast, errors);

                if (!errors.isEmpty()) {
                    errors.forEach(System.out::println);
                } else {
                    // Armar objeto para solicitudes
                    if(!map.containsKey("nombre")) {
                        
                    } else {
                    
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private HashMap<String, Request> checkAst(Token info, List<Request> ast, List<Err> errors) {
        HashMap<String, Request> map = new HashMap<>();
        for (Request r : ast) {
            if (r instanceof RequestName) {
                checkMap(map, "nombre", r, errors);
            }

            if (r instanceof Type) {
                checkMap(map, "tipo", r, errors);
            }
        }

        if (!map.containsKey("tipo")) {
            Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), "tipo");
            String description = "En la solicitud no se ha indicado el tipo(Lista, Pista), este valor es obligatorio.";
            err.setDescription(description);
            errors.add(err);
        }

        return map;
    }

    private void checkMap(HashMap<String, Request> map, String key, Request value, List<Err> errors) {
        if (!map.containsKey(key)) {
            map.put(key, value);
        } else {
            // Error, ya se indico key para el solicitud
            Token t = value.getInfo();
            Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), key);
            String description = "La opcion `" + key + "` ya se ha indicado en la solicitud";
            err.setDescription(description);
            errors.add(err);
        }
    }
}
