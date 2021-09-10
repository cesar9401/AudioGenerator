package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.model.Track;
import com.cesar31.audiogenerator.parser.Token;
import com.cesar31.audiogenerator.parser.playlist.PlaylistLex;
import com.cesar31.audiogenerator.parser.playlist.PlaylistParser;
import com.cesar31.audiogenerator.playlist.Name;
import com.cesar31.audiogenerator.playlist.Option;
import com.cesar31.audiogenerator.playlist.TrackList;
import com.cesar31.audiogenerator.ui.MainView;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import com.cesar31.audiogenerator.playlist.MusicList;
import com.cesar31.audiogenerator.playlist.Playlist;

/**
 *
 * @author csart
 */
public class PlaylistParserHandler {

    private MainControl control;
    private MainView view;
    private Playlist play;

    private List<String> music;

    public PlaylistParserHandler(MainControl control, MainView view) {
        this.control = control;
        this.view = view;
    }

    /**
     * Parsear entrada
     *
     * @param data
     * @return
     */
    public List<Err> parseSource(String data) {
        long t = System.currentTimeMillis();
        PlaylistLex lex = new PlaylistLex(new StringReader(data));
        PlaylistParser parser = new PlaylistParser(lex);
        try {
            List<MusicList> ast = (List<MusicList>) parser.parse().value;
            Token info = parser.getInfo();
            List<Err> errors = parser.getErrors();

            if (!errors.isEmpty()) {
                return errors;
            } else {
                errors = new ArrayList<>();
                // Revisar errores sintacticos aca
                HashMap<String, MusicList> map = checkAst(info, ast, errors);

                if (!errors.isEmpty()) {
                    return errors;
                } else {
                    // Errores verificados, crear playlist
                    String name = ((Name) map.get("nombre")).getName();
                    boolean circular = map.containsKey("circular") ? ((Option) map.get("circular")).isValue() : false;
                    boolean random = map.containsKey("random") ? ((Option) map.get("random")).isValue() : false;
                    music = music == null ? new ArrayList<>() : music;

                    // Lista de reproduccion para guardar
                    this.play = new Playlist(name, random, circular, music);
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "No es posible compilar, por favor verifique su codigo fuente.");
            // ex.printStackTrace(System.out);
        }

        return new ArrayList<>();
    }

    /**
     * Revisar ast
     *
     * @param ast
     * @param errors
     */
    private HashMap<String, MusicList> checkAst(Token info, List<MusicList> ast, List<Err> errors) {
        HashMap<String, MusicList> map = new HashMap<>();
        for (MusicList p : ast) {
            if (p instanceof Name) {
                checkMap(map, "nombre", p, errors);
            }

            if (p instanceof TrackList) {
                checkMap(map, "pistas", p, errors);
            }

            if (p instanceof Option) {
                Option.Type type = ((Option) p).getType();
                if (type == Option.Type.CIRCULAR) {
                    checkMap(map, "circular", p, errors);
                } else {
                    checkMap(map, "random", p, errors);
                }
            }
        }

        // Revisar nombre
        if (!map.containsKey("nombre")) {
            Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), "nombre");
            String description = "En la definicion de lista de reproducción, no se ha indicado el atributo nombre, este es obligatorio.";
            err.setDescription(description);
            errors.add(err);
        }

        // Revisar pistas
        if (map.containsKey("pistas")) {
            // Revisar pistas repetidas
            HashMap<String, Token> songs = new HashMap<>();
            TrackList list = (TrackList) map.get("pistas");
            for (Token t : list.getList()) {
                if (!songs.containsKey(t.getValue())) {
                    songs.put(t.getValue(), t);
                } else {
                    // Error, la pista ya fue agregada
                    Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                    String description = "La pista `" + t.getValue() + "` ya fue agregada a la playlist, no es posible agregarla dos veces";
                    err.setDescription(description);
                    errors.add(err);
                }
            }

            // Revisar que las pistas existan en la biblioteca
            List<Track> tracks = control.getFile().read();
            music = new ArrayList<>();
            // Revisar que las pistas existan
            songs.forEach((String key, Token value) -> {
                if (tracks != null && containsSong(tracks, key)) {
                    music.add(key);
                } else {
                    // Error, la pista no esta definida en la biblioteca
                    Err err = new Err(Err.TypeErr.SEMANTICO, value.getLine(), value.getColumn(), value.getValue());
                    String description = "La pista con el nombre: `" + key + "` no esta definida en la biblioteca.";
                    err.setDescription(description);
                    errors.add(err);
                }
            });
        }

        return map;
    }

    /**
     * Revisar si el nombre de una pista esta en la biblioteca
     *
     * @param tracks
     * @param name
     * @return
     */
    public boolean containsSong(List<Track> tracks, String name) {
        return tracks.stream().anyMatch(track -> track.getName().equals(name));
    }

    /**
     * Revisar que no hayan opciones o atributos repetidos
     *
     * @param map
     * @param key
     * @param value
     * @param errors
     */
    private void checkMap(HashMap<String, MusicList> map, String key, MusicList value, List<Err> errors) {
        if (!map.containsKey(key)) {
            map.put(key, value);
        } else {
            // Error, ya se indico key para la playlist
            Token t = value.getInfo();
            Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
            String description = "El atributo `" + t.getValue() + "` ya ha sido indicado.";
            err.setDescription(description);
            errors.add(err);
        }
    }

    public Playlist getPlay() {
        return play;
    }
}
