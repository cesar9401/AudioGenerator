package com.cesar31.audiogeneratorclient.control;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cesar31.audiogeneratorclient.model.Err;
import com.cesar31.audiogeneratorclient.model.Playlist;
import com.cesar31.audiogeneratorclient.model.Track;
import com.cesar31.audiogeneratorclient.parser.ClientLex;
import com.cesar31.audiogeneratorclient.parser.ClientParser;

import java.io.StringReader;
import java.util.List;

public class ResponseParserHandler {

    public ResponseParserHandler() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseResponse(String input) {
        ClientLex lex = new ClientLex(new StringReader(input));
        ClientParser parser = new ClientParser(lex);
        try {
            parser.parse();
            if(parser.getErrors() != null) {
                // Errores
                List<Err> errors = parser.getErrors();
                errors.forEach(System.out::println);

            } else if(parser.getPlaylists() != null) {
                // listado de listas
                List<Playlist> playlists = parser.getPlaylists();
                playlists.forEach(System.out::println);

            } else if(parser.getTracks() != null) {
                // listado de pistas
                List<Track> tracks = parser.getTracks();
                tracks.forEach(System.out::println);

            } else if(parser.getPlaylist() != null) {
                // playlist especifica con listado de pistas
                Playlist play = parser.getPlaylist();
                System.out.println(play.toString());
                play.getTracks().forEach(System.out::println);

            } else if(parser.getTrack() != null) {
                // pista con sus respectivas notas
                Track track = parser.getTrack();
                System.out.println(track.toString());
                track.getNotes().forEach(System.out::println);

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
