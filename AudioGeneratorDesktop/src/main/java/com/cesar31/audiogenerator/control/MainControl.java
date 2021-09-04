package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.model.Note;
import com.cesar31.audiogenerator.model.Song;
import com.cesar31.audiogenerator.model.Sound;
import com.cesar31.audiogenerator.model.Track;
import com.cesar31.audiogenerator.ui.MainView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

/**
 *
 * @author cesar31
 */
public class MainControl {

    private final String PATH = "tracks.dat";
    private List<Track> songs;
    private Track track;

    private Pattern p;
    private int count;
    private Song s;
    private Position pos;

    public MainControl() {
    }

    public void initWindow() {
        java.awt.EventQueue.invokeLater(() -> {
            MainView view = new MainView(this);
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }

    /**
     * Enviar texto para parseo (Lenguaje principal)
     *
     * @param input
     * @param log
     * @param view
     */
    public void parseSource(String input, JTextArea log, MainView view) {
        track = null;
        if (!input.isEmpty() || !input.isBlank()) {
            ParserHandler parser = new ParserHandler(log);
            List<Err> errors = parser.parseSource(input);

            if (errors.isEmpty()) {
                this.track = parser.getTrack();
                List<Track> tracks = read();
                if (track != null) {
                    boolean nameOcuped = tracks != null && tracks.contains(track);
                    if (nameOcuped) {
                        // Mensaje, cambiar de nombre
                        JOptionPane.showMessageDialog(view, "El archivo ha sido compilado con exito, pero el nombre de la pista no esta disponible, intente con otro nombre");
                    } else {
                        // Habilitar boton
                        JOptionPane.showMessageDialog(view, "Archivo compilado con exito, el boton para guardar la pista se ha habilidato.");
                        view.saveTrack.setEnabled(true);
                    }
                }
            } else {
                view.setTableErrors(errors);
                view.tabbed.setSelectedIndex(1);
            }
        }
    }

    public void saveTrack(MainView view) {
        if (track != null) {
            System.out.println("Guardar pista aqui :v");
            write(track);
        }
    }

    private void write(Track tmp) {
        List<Track> tracks = read();
        if (tracks == null) {
            tracks = new ArrayList<>();
        }
        tracks.add(tmp);
        System.out.println("Tracks -> " + tracks.size());
        // Escribir aqui
        try {
            try ( ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(this.PATH))) {
                file.writeObject(tracks);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private List<Track> read() {
        try {
            try ( ObjectInputStream file = new ObjectInputStream(new FileInputStream(this.PATH))) {
                List<Track> tracks = (List<Track>) file.readObject();
                return tracks;
            }
        } catch (FileNotFoundException ex) {
            // ex.printStackTrace(System.out);
            System.out.println("No se encontro archivo, enviando null");
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }

        return null;
    }

    public void updateSongs(MainView view) {
        // Reproductor
        if (view.tabbed.getSelectedIndex() == 2) {
            songs = read();
            if (songs != null) {
                DefaultListModel model = new DefaultListModel();
                songs.forEach(song -> model.addElement(song.getName()));
                view.musicList.setModel(model);
            }
        }
    }

    public void playSong(int index) {
        if (index != -1) {
            if (songs != null) {
                Track tmp = songs.get(index);
                System.out.println("Duration: " + tmp.getDuration());
                List<Sound> sounds = tmp.getSounds();

                s = renderSounds(sounds);
                pos = new Position(s.getPlayer());
                
                //s.setPos(pos);
                
                s.start();
                //System.out.println("here");
                //pos.start();
            }
        }
    }

    public void pause() {
        pos.start();
    }

    private Song renderSounds(List<Sound> sounds) {
        List<Note> notes = new ArrayList<>();
        HashMap<Integer, String> map = new HashMap<>();
        for (Sound s : sounds) {
            double milli = s.getMilliseconds() / 1000d;
            String note = s.getNote() + s.getEighth() + "/" + milli + " ";
            if (!map.containsKey(s.getChannel())) {
                map.put(s.getChannel(), note);
            } else {
                note = map.get(s.getChannel()) + note;
                map.put(s.getChannel(), note);
            }
        }

        count = 0;
        p = new Pattern();
        map.forEach((key, note) -> {
            String s = "T240 v" + count + " " + getInstrument(count) + " " + note + " ";
            // System.out.println(s);
            p.add(s);
            count++;

            if (count == 16) {
                count = 0;
                // Agregar a pattern a nota
                notes.add(new Note(new Player(), p));
                p = new Pattern();
            }
        });

        if (count != 0) {
            notes.add(new Note(new Player(), p));
            //System.out.println(p.toString());
        }

        return new Song(notes);
    }

    /**
     * Obtener instrumento
     *
     * @param n indice del instrumento a obtener
     * @return
     */
    private String getInstrument(int n) {
        String[] i = {"ROCK_ORGAN", "TRUMPET", "ACOUSTIC_BASS", "VIOLIN", "CLARINET", "FLUTE", "BANJO", "STEEL_STRING_GUITAR",
            "ELECTRIC_JAZZ_GUITAR", "ELECTRIC_CLEAN_GUITAR", "TROMBONE", "TUBA", "PIANO", "GUITAR", "ELECTRIC_PIANO", "MARIMBA"};

        return "I[" + i[n] + "]";
    }

    public Track getTrack() {
        return track;
    }
}
