package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.model.Song;
import com.cesar31.audiogenerator.model.Track;
import com.cesar31.audiogenerator.playlist.Playlist;
import com.cesar31.audiogenerator.ui.MainView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author cesar31
 */
public class MainControl {

    private FileControl file;
    private List<Track> songs;
    private List<Playlist> playlist;
    private List<Track> tmpSongs;
    private Track track;

    private boolean random;
    private boolean circular;

    private Song s;
    private boolean play;

    public MainControl() {
        this.file = new FileControl();
        this.play = false;

        this.random = false;
        this.circular = false;
    }

    public void initWindow() {
        java.awt.EventQueue.invokeLater(() -> {
            MainView view = new MainView(this);
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }

    public void parse(String input, MainView view) {
        if (!input.isEmpty() || !input.isBlank()) {
            int index = view.typeCombo.getSelectedIndex();
            if (index == 0) {
                // parser lenguaje definicion de pistas
                parseSource(input, view);
            } else if (index == 1) {
                // parsear lenguaje definicion de listas
                parseDefList(input, view);
            }
        }
    }

    /**
     * Parsear entrada para definicion de listas de reproduccion
     *
     * @param input
     * @param view
     */
    private void parseDefList(String input, MainView view) {
        PlaylistParserHandler parser = new PlaylistParserHandler(this, view);
        List<Err> errors = parser.parseSource(input);

        if (errors.isEmpty()) {
            view.setTableErrors(new ArrayList<>());
            Playlist newList = parser.getPlay();

            List<Playlist> list = file.readPlaylists();
            if (newList != null) {
                boolean nameOcuped = list != null && list.contains(newList);
                if (nameOcuped) {
                    // Mensaje, cambiar de nombre
                    JOptionPane.showMessageDialog(view, "El archivo ha sido compilado con exito, pero el nombre de la lista de reproduccion no esta disponible, intente con otro nombre");
                } else {
                    file.writePlayList(newList);
                    String message = "Se ha guardado con exito la lista de reproduccion: " + newList.getName() + ".";
                    JOptionPane.showMessageDialog(view, message);
                }
            }

        } else {
            view.setTableErrors(errors);
            view.tabbed.setSelectedIndex(1);
        }
    }

    /**
     * Enviar texto para parseo (Definicion de pistas)
     *
     * @param input
     * @param log
     * @param view
     */
    private void parseSource(String input, MainView view) {
        track = null;
        ParserHandler parser = new ParserHandler(view);
        List<Err> errors = parser.parseSource(input);

        if (errors.isEmpty()) {
            view.setTableErrors(new ArrayList<>());
            this.track = parser.getTrack();
            List<Track> tracks = file.read();
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

    /**
     * Guardar pista recien compilada
     *
     * @param view
     */
    public void saveTrack(MainView view) {
        if (track != null) {
            file.write(track);
            // Se ha guardado la pista
            String message = "Se ha guardado con exito la pista: " + track.getName() + ".";
            JOptionPane.showMessageDialog(view, message);
        }
    }

    /**
     * Escribir todas las canciones en la base de datos
     *
     * @param tmp
     */
    /**
     * Actualizar canciones en biblioteca
     *
     * @param view
     */
    public void updateSongs(MainView view) {
        // Actualizar listado en reproductor
        if (view.tabbed.getSelectedIndex() == 2) {
            songs = file.read();
            if (songs != null) {
                DefaultListModel model = new DefaultListModel();
                songs.forEach(song -> model.addElement(song.getName() + "  -  " + (int) Math.floor(song.getDuration() / 1000d) + " seg"));
                view.musicList.setModel(model);
            }

            playlist = file.readPlaylists();
            if (playlist != null) {
                DefaultListModel model = new DefaultListModel();
                playlist.forEach(list -> {
                    String in = list.getName() + " (";
                    in += list.isCircular() ? "C" : "";
                    in += list.isRandom() ? "R" : "";
                    in += " " + list.getPlaylist().size() + " canciones)";
                    model.addElement(in);
                });
                view.playList.setModel(model);
            }
        }
    }

    /**
     * Actualizar listado de canciones al hacer doble click en lista de
     * reproduccion
     *
     * @param index
     * @param view
     */
    public void updateSongsList(int index, MainView view) {
        if (index != -1) {
            if (playlist != null) {
                Playlist tmp = playlist.get(index);
                if (tmp != null) {
                    this.random = tmp.isRandom();
                    this.circular = tmp.isCircular();

                    tmpSongs = file.getList(tmp);
                    DefaultListModel model = new DefaultListModel();
                    tmpSongs.forEach(song -> model.addElement(song.getName() + "  -  " + (int) Math.floor(song.getDuration() / 1000d) + " seg"));
                    view.songsList.setModel(model);
                }
            }
        }
    }

    /**
     * Reproducir lista actual
     *
     * @param index
     * @param view
     */
    public void playSong(int index, MainView view) {
        if (index != -1) {
            //System.out.println(index);
            if (songs != null) {
                if (s != null && s.isAlive()) {
                    s.stop();
                }

                play = true;
                view.playPause.setText("Pause");
                s = new Song(index, songs, false, false, view);
                s.start();
            }
        }
    }

    public void playPlayList(int index, MainView view) {
        if (index != -1) {
            if (tmpSongs != null) {
                if (s != null && s.isAlive()) {
                    s.stop();
                }

                play = true;
                view.playPause.setText("Pause");
                s = new Song(index, tmpSongs, this.random, this.circular, view);
                s.start();
            }
        }
    }

    /**
     * Play or Pause
     *
     * @param view
     */
    public void play(MainView view) {
        // Resume
        if (s != null && s.isAlive()) {
            if (play) {
                play = !play;
                view.playPause.setText("Play");
                s.pause();
            } else {
                play = !play;
                view.playPause.setText("Pause");
                s.play();
            }
        }
    }

    public Track getTrack() {
        return track;
    }

    public FileControl getFile() {
        return file;
    }
}
