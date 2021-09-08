package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.model.Sound;
import com.cesar31.audiogenerator.model.Track;
import com.cesar31.audiogenerator.parser.Token;
import com.cesar31.audiogenerator.parser.request.RequestLex;
import com.cesar31.audiogenerator.parser.request.RequestParser;
import com.cesar31.audiogenerator.playlist.Playlist;
import com.cesar31.audiogenerator.request.Request;
import com.cesar31.audiogenerator.request.RequestName;
import com.cesar31.audiogenerator.request.Type;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author csart
 */
public class RequestParserHandler {

    private FileControl file;
    private String response;
    private String aux;

    public RequestParserHandler() {
        this.file = new FileControl();
    }

    public String parseSource(String data) {
        RequestLex lex = new RequestLex(new StringReader(data));
        RequestParser parser = new RequestParser(lex);
        try {
            //parser.parse();
            List<Request> ast = (List<Request>) parser.parse().value;
            Token info = parser.getInfo();
            List<Err> errors = parser.getErrors();

            if (!errors.isEmpty()) {
                //errors.forEach(System.out::println);
                response = responseError(errors);
                return response;
            } else {
                errors = new ArrayList<>();

                // Revisar errores sintacticos aqui
                HashMap<String, Request> map = checkAst(info, ast, errors);

                if (!errors.isEmpty()) {
                    // errors.forEach(System.out::println);
                    response = responseError(errors);
                    return response;
                } else {
                    // Armar objeto para solicitudes
                    Type t = (Type) map.get("tipo");
                    RequestName n = map.containsKey("nombre") ? (RequestName) map.get("nombre") : null;

                    // Verificar existencia aca
                    errors = checkStock(t, n);
                    if (!errors.isEmpty()) {
                        // errors.forEach(System.out::println);
                        response = responseError(errors);
                        return response;
                    } else {
                        return response;
                    }
                }
            }
        } catch (Exception ex) {
            // Reportar error
            ex.printStackTrace(System.out);
            return this.exceptionErr();
        }
    }

    private List<Err> checkStock(Type t, RequestName n) {
        List<Err> errors = new ArrayList<>();
        String type = t.getType().getValue();

        List<Playlist> play = null;
        List<Track> tracks = null;

        switch (type) {
            case "Lista":
                play = file.readPlaylists();
                if (play == null) {
                    // Error base de datos vacia
                    Token tmp = t.getType();
                    Err err = new Err(Err.TypeErr.EJECUCION, tmp.getLine(), tmp.getColumn(), tmp.getValue());
                    String description = "El servidor no posee datos de listas de reproduccion, intente mas tarde.";
                    err.setDescription(description);
                    errors.add(err);
                    return errors;
                }
                break;
            case "Pista":
                tracks = file.read();
                if (tracks == null) {
                    // Error base de datos vacia
                    Token tmp = t.getType();
                    Err err = new Err(Err.TypeErr.EJECUCION, tmp.getLine(), tmp.getColumn(), tmp.getValue());
                    String description = "El servidor no posee datos de pistas, intente mas tarde.";
                    err.setDescription(description);
                    errors.add(err);
                    return errors;
                }
                break;
        }

        // Si no hay nombres
        if (n == null) {
            // Enviar todas las pistas o todas las listas
            switch (type) {
                case "Lista":
                    // Construir respuesta con play
                    response = responseWithAllLists(play);
                    break;
                case "Pista":
                    // Construir respuesta con tracks
                    response = responseWithAllTracks(tracks);
                    break;
            }

            return errors;
        }

        String name = n.getName().getValue();
        switch (type) {
            case "Lista":
                if (play != null) {
                    Optional<Playlist> op = play.stream().filter(p -> p.getName().equals(name)).findAny();
                    //boolean present = play.stream().anyMatch(p -> p.getName().equals(name));
                    if (op.isEmpty()) {
                        // Error la lista de reproduccion no existe
                        Token tmp = n.getName();
                        Err err = new Err(Err.TypeErr.EJECUCION, tmp.getLine(), tmp.getColumn(), tmp.getValue());
                        String description = "La lista `" + name + "` no existe en la base de datos.";
                        err.setDescription(description);
                        errors.add(err);

                        return errors;
                    } else {
                        Playlist p = op.get();
                        // Construir respuesta con p
                        response = responseWithOneList(p);
                    }
                }

                break;
            case "Pista":
                if (tracks != null) {
                    //boolean present = tracks.stream().anyMatch(s -> s.getName().equals(name));
                    Optional<Track> op = tracks.stream().filter(s -> s.getName().equals(name)).findAny();
                    if (op.isEmpty()) {
                        // Error la pista no existe
                        Token tmp = n.getName();
                        Err err = new Err(Err.TypeErr.EJECUCION, tmp.getLine(), tmp.getColumn(), tmp.getValue());
                        String description = "La pista `" + name + "` no existe en la base de datos.";
                        err.setDescription(description);
                        errors.add(err);
                        return errors;
                    } else {
                        Track track = op.get();
                        // Construir respuesta con track
                        response = responseWithOneTrack(track);
                    }
                }
                break;
        }

        return errors;
    }

    /**
     * Revisar ast en busca de errores sintacticos
     *
     * @param info
     * @param ast
     * @param errors
     * @return
     */
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

    /**
     * Revisar que las opciones para las solicitudes solo esten presentes una
     * vez
     *
     * @param map
     * @param key
     * @param value
     * @param errors
     */
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

    /**
     * Respuesta con todas las listas
     *
     * @param list
     * @return
     */
    private String responseWithAllLists(List<Playlist> list) {
        String res = "<listas>\n";
        for (Playlist p : list) {
            res += "\t<lista nombre = \"" + p.getName() + "\" pistas = " + p.getPlaylist().size() + " random = " + p.isRandom() + " circular = " + p.isCircular() + ">\n";
        }
        res += "</listas>";
        return res;
    }

    /**
     * Respuesta con todas las canciones
     *
     * @param list
     * @return
     */
    private String responseWithAllTracks(List<Track> list) {
        String res = "<pistas>\n";
        for (Track t : list) {
            res += "\t<pista nombre = \"" + t.getName() + "\" duracion = " + t.getDuration() + ">\n";
        }
        res += "</pistas>";
        return res;
    }

    /**
     * Respuesta con una pista
     *
     * @param t
     * @return
     */
    private String responseWithOneTrack(Track t) {
        HashMap<Integer, String> map = new HashMap<>();
        for (Sound s : t.getSounds()) {
            int channel = s.getChannel();
            String r = "\t\t<nota nombre = \"" + s.getNote() + "\" octava = \"" + s.getEighth() + "\" duracion = " + s.getMilliseconds() + ">\n";
            if (!map.containsKey(channel)) {
                map.put(channel, r);
            } else {
                r = map.get(channel) + r;
                map.put(channel, r);
            }
        }

        aux = "";
        String res = "<pista nombre = \"" + t.getName() + "\" duracion = " + t.getDuration() + ">\n";
        map.forEach((Integer key, String value) -> {
            aux += "\t<canal numero = " + key + ">\n";
            aux += value;
            aux += "\t</canal>\n";
        });
        res += aux;
        res += "</pista>";
        return res;
    }

    /**
     * Respuesta con una lista
     *
     * @param list
     * @return
     */
    private String responseWithOneList(Playlist list) {
        List<Track> tracklist = file.getList(list);
        String res = "<lista nombre = \"" + list.getName() + "\" pistas = " + list.getPlaylist().size() + " random = " + list.isRandom() + " circular = " + list.isCircular() + ">\n";
        for (Track t : tracklist) {
            res += "\t<pista nombre = \"" + t.getName() + "\" duracion = " + t.getDuration() + ">\n";
        }
        res += "</lista>";
        return res;
    }

    /**
     * Respuesta de errores
     *
     * @param errors
     * @return
     */
    private String responseError(List<Err> errors) {
        String res = "<errores>\n";
        for (Err e : errors) {
            res += "\t<error>\n";
            res += "\t\t<tipo>" + e.getType().toString() + "</tipo>\n";
            res += "\t\t<linea>" + e.getLine() + "</linea>\n";
            res += "\t\t<columna>" + e.getColumn() + "</columna>\n";
            res += "\t\t<lexema>\"" + e.getLexema() + "\"</lexema>\n";
            res += "\t\t<descripcion>\"" + e.getDescription() + "\"</descripcion>\n";
            res += "\t</error>\n";
        }
        res += "</errores>";
        return res;
    }

    private String exceptionErr() {
        String res = "<errores>\n";
        res += "\t<error>\n";
        res += "\t\t<tipo>" + Err.TypeErr.EJECUCION.toString() + "</tipo>\n";
        res += "\t\t<linea>" + 0 + "</linea>\n";
        res += "\t\t<columna>" + 0 + "</columna>\n";
        res += "\t\t<lexema>\"" + "-" + "\"</lexema>\n";
        res += "\t\t<descripcion>\"" + "No es posible procesar la peticion, por favor verifique la sintaxis de la solicitud." + "\"</descripcion>\n";
        res += "\t</error>\n";
        res += "</errores>";
        return res;
    }
}
