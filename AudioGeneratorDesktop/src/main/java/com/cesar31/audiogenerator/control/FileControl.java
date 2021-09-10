package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.model.Track;
import com.cesar31.audiogenerator.playlist.Playlist;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author cesar31
 */
public class FileControl {

    private final String PATH = "tracks.dat";
    private final String PLAYLISTS_PATH = "playlists.dat";

    public FileControl() {
    }

    /**
     * Leer archivo de texto
     *
     * @param path direccion donde esta el archivo
     * @return
     */
    public static String readData(String path) {
        String string = "";
        File file = new File(path);
        try {
            try ( BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null) {
                    string += line;
                    line = br.readLine();

                    if (line != null) {
                        string += "\n";
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return string;
    }

    /**
     * Imprimir o guarar archivo segun path y texto
     *
     * @param file direccion del archivo
     * @param txt texto a guardar
     */
    public void writeFile(File file, String txt) {
        try {
            try ( PrintWriter writer = new PrintWriter(file)) {
                writer.write(txt);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Obtener informacion de linea y columna
     *
     * @param component
     * @return
     */
    public String lineAndColumnInfo(JTextArea component) {
        String info = "";
        int linea = 1;
        int columna = 1;

        int caretPos = component.getCaretPosition();
        try {
            linea = component.getLineOfOffset(caretPos);
            columna = caretPos - component.getLineStartOffset(linea);
            linea++;
        } catch (BadLocationException ex) {
            ex.printStackTrace(System.out);
        }

        info = "Linea: " + linea + ", Columna: " + columna;
        return info;
    }

    public void write(Track tmp) {
        List<Track> tracks = read();
        if (tracks == null) {
            tracks = new ArrayList<>();
        }
        tracks.add(tmp);
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

    /**
     * Escribir todas las pistas actuales
     *
     * @param tracks pistas a guardar en db
     */
    public void writeAll(List<Track> tracks) {
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

    /**
     * Escribir todas las listas de reproduccion actuales
     *
     * @param playlist listas a guardar en db
     */
    public void writeAllPlaylists(List<Playlist> playlist) {
        try {
            try ( ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(this.PLAYLISTS_PATH))) {
                file.writeObject(playlist);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Leer todas las canciones en la base de datos
     *
     * @return
     */
    public List<Track> read() {
        try {
            try ( ObjectInputStream file = new ObjectInputStream(new FileInputStream(this.PATH))) {
                List<Track> tracks = (List<Track>) file.readObject();
                return tracks;
            }
        } catch (FileNotFoundException ex) {
            // ex.printStackTrace(System.out);
            // System.out.println("No se encontro archivo, enviando null");
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }

        return null;
    }

    /**
     * Escribir archivo binario con listas de reproduccion
     *
     * @param tmp
     */
    public void writePlayList(Playlist tmp) {
        List<Playlist> playlist = readPlaylists();
        if (playlist == null) {
            playlist = new ArrayList<>();
        }
        playlist.add(tmp);
        // Escribir aqui
        try {
            try ( ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(this.PLAYLISTS_PATH))) {
                file.writeObject(playlist);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Leer archivo binario con listas de reproduccion
     *
     * @return
     */
    public List<Playlist> readPlaylists() {
        try {
            try ( ObjectInputStream file = new ObjectInputStream(new FileInputStream(this.PLAYLISTS_PATH))) {
                List<Playlist> playlist = (List<Playlist>) file.readObject();
                return playlist;
            }
        } catch (FileNotFoundException ex) {
            // ex.printStackTrace(System.out);
            // System.out.println("No se encontro archivo, enviando null");
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
        }

        return null;
    }

    /**
     * Obtener un listado
     *
     * @param tmp
     * @return
     */
    public List<Track> getList(Playlist tmp) {
        List<Track> list = this.read();
        List<Track> myList = new ArrayList<>();

        if (list != null) {
            for (String s : tmp.getPlaylist()) {
                Optional<Track> opt = list.stream().filter(t -> t.getName().equals(s)).findFirst();
                if (opt.isPresent()) {
                    myList.add(opt.get());
                }
            }
        }

        return myList;
    }
}
