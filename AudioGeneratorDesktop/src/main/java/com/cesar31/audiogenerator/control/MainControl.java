package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.model.Song;
import com.cesar31.audiogenerator.model.Track;
import com.cesar31.audiogenerator.playlist.Playlist;
import com.cesar31.audiogenerator.ui.MainView;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
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

    private Listener listener;

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

    /**
     * Se inicia el escuchador de peticiones
     */
    public void initListener() {
        int portServer = 8080; // puerto donde el servidor escucha
        int portClient = 8081; // puerto donde el cliente escucha
        String ipClient = "192.168.10.103"; // ip del cliente
        
        // Imprimir ip's
        System.out.println("IP cliente: " + ipClient);
        getIP();

        this.listener = new Listener(portServer, ipClient, portClient);
        this.listener.start();
    }

    private void getIP() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("IP local: " + address.getHostAddress());
        } catch (UnknownHostException ex) {
            //ex.printStackTrace(System.out);
        }
    }

    public void setIpClient(String ip) {
        if (this.listener != null) {
            if (ip != null) {
                if (ip.length() > 0 && ip.trim().length() > 0) {
                    this.listener.setIpClient(ip.trim());
                    System.out.println("Cambiando a: " + ip);
                }
            }
        }
    }

    public void parse(String input, MainView view) {
        stop(view); // Cortar reproduccion
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
            } else if (!parser.hasPrincipal()) {
                JOptionPane.showMessageDialog(view, "El archivo no tiene metodo principal, no se ha generado ninguna pista.");
                // System.out.println("No tiene metodo principal");
            } else if (track == null) {
                JOptionPane.showMessageDialog(view, "Archivo compilado, no se genero ninguna pista.");
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
     * Reproducir biblioteca actual
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

    /**
     * Reproducir playlist
     *
     * @param index
     * @param view
     */
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
        } else if (s != null) {
            // s.start();
        }
    }

    /**
     * Detener reproduccion
     *
     * @param view
     */
    public void stop(MainView view) {
        if (s != null && s.isAlive()) {
            play = false;
            view.playPause.setText("Play");
            view.progressBar.setValue(0);
            view.progressBar.setString("0 s");
            s.stopping();
        }

        if (s != null && s.isAlive()) {
            // System.out.println("still alive");
            s.stop();
        }
    }

    /**
     * Eliminar pista de biblioteca
     *
     * @param view
     * @param index
     */
    public void deleteFromLibrary(MainView view, int index) {
        if (index != -1) {
            if (songs != null && songs.size() > index) {
                stop(view); // Detener reproduccion

                Track tmp = songs.get(index);// obtener pista a eliminar
                songs = file.read();
                songs.remove(tmp); // eliminar de listado

                // Escribir en db
                file.writeAll(songs);

                // Eliminar tmp de cada lista de reproduccion
                this.playlist = file.readPlaylists();
                if (playlist != null) {
                    for (Playlist p : playlist) {
                        if (p.getPlaylist() != null && p.getPlaylist().contains(tmp.getName())) {
                            p.getPlaylist().remove(tmp.getName()); //Eliminar tmp de cada lista
                            // System.out.println(tmp.getName() + " deleted from " + p.getName());
                        }
                    }

                    // grabar songs and playlists
                    file.writeAllPlaylists(playlist);
                }

                // Actualizar vista
                updateSongs(view);
                view.songsList.setModel(new DefaultListModel<>());
            }
        }
    }

    public void deletePlaylist(MainView view, int index) {
        if (index != -1) {
            if (playlist != null && playlist.size() > index) {
                stop(view); // Detener reproduccion
                Playlist tmp = playlist.get(index);
                playlist.remove(tmp);
                file.writeAllPlaylists(playlist);

                updateSongs(view);
                view.songsList.setModel(new DefaultListModel<>());
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
