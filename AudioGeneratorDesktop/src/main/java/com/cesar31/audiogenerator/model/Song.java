package com.cesar31.audiogenerator.model;

import com.cesar31.audiogenerator.ui.MainView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JProgressBar;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

/**
 *
 * @author cesar31
 */
public class Song extends Thread {

    private int index;
    private List<Track> tracks;
    private boolean random;
    private boolean circular;

    private List<Note> notes;
    private MainView view;
    private JProgressBar bar;

    // Para construir la pista
    private Pattern p;
    private int count;

    public Song(int index, List<Track> tracks, boolean random, boolean circular, MainView view) {
        this.index = index;
        this.tracks = tracks;
        this.random = random;
        this.circular = circular;

        this.bar = view.progressBar;
        this.view = view;
    }

    @Override
    public void run() {
        int count = 0;
        int i = index;
        while (count < tracks.size()) {
            notes = renderSounds(tracks.get(i).getSounds());
            view.songLabel.setText(tracks.get(i).getName());

            int duration = (int) (tracks.get(i).getDuration() / 1000);
            initValues(duration);

            for (Note n : notes) {
                n.start();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.out);
            }
            while (getPlayer().getManagedPlayer().isPlaying()) {
                try {
                    int time = (int) (getPlayer().getManagedPlayer().getTickPosition() * duration / getPlayer().getManagedPlayer().getTickLength());
                    //System.out.println(time);
                    bar.setValue(time);
                    bar.setString(time + " s");
                    bar.setStringPainted(true);
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace(System.out);
                }

                while (getPlayer().getManagedPlayer().isPaused()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace(System.out);
                    }
                }
            }

            if (getPlayer().getManagedPlayer().isFinished()) {
                bar.setValue(duration);
                bar.setString(duration + " s");
                bar.setStringPainted(true);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.out);
            }

            if (view.repeatCheck.isSelected()) {
                // Seguir reproduciendo i
            } else if (random && circular) {
                i = (int) Math.floor(Math.random() * tracks.size());
            } else if (random) {
                i = (int) Math.floor(Math.random() * tracks.size());
                count++;
            } else if (circular) {
                i = i < tracks.size() - 1 ? i + 1 : 0;
                count = i;
            } else {
                i++;
                count = i;
            }
        }
    }

    public void pause() {
        if (getPlayer().getManagedPlayer().isPlaying()) {
            getPlayer().getManagedPlayer().pause();
        }
    }

    public void play() {
        if (getPlayer().getManagedPlayer().isPaused()) {
            getPlayer().getManagedPlayer().resume();
        }
    }

    public void initValues(int duration) {
        this.bar.setValue(0);
        this.bar.setString("0 s");
        this.bar.setStringPainted(true);
        this.bar.setMaximum(duration);
    }

    public Player getPlayer() {
        return notes.get(0).getPlayer();
    }

    private List<Note> renderSounds(List<Sound> sounds) {
        List<Note> tmp = new ArrayList<>();
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
                tmp.add(new Note(new Player(), p));
                p = new Pattern();
            }
        });

        if (count != 0) {
            tmp.add(new Note(new Player(), p));
            //System.out.println(p.toString());
        }

        return tmp;
    }

    private String getInstrument(int n) {
        String[] i = {"ROCK_ORGAN", "TRUMPET", "ACOUSTIC_BASS", "VIOLIN", "CLARINET", "FLUTE", "BANJO", "STEEL_STRING_GUITAR",
            "ELECTRIC_JAZZ_GUITAR", "ELECTRIC_CLEAN_GUITAR", "TROMBONE", "TUBA", "PIANO", "GUITAR", "ELECTRIC_PIANO", "MARIMBA"};

        return "I[" + i[n] + "]";
    }
}
