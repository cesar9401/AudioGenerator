package com.cesar31.audiogenerator.model;

import java.util.List;
import javax.swing.JProgressBar;
import org.jfugue.player.Player;

/**
 *
 * @author cesar31
 */
public class Song extends Thread {

    protected int duration;
    protected List<Note> notes;
    protected JProgressBar bar;

    public Song(List<Note> notes, JProgressBar bar, double duration) {
        this.notes = notes;
        this.bar = bar;
        this.duration = (int) (duration / 1000);
        initValues();
    }

    @Override
    public void run() {
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
                int time = (int) (getPlayer().getManagedPlayer().getTickPosition() * this.duration / getPlayer().getManagedPlayer().getTickLength());
                System.out.println(time);
                bar.setValue(time);
                bar.setString(time + " s");
                bar.setStringPainted(true);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.out);
            }
            
            while(getPlayer().getManagedPlayer().isPaused()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }

        if (getPlayer().getManagedPlayer().isFinished()) {
            bar.setValue(this.duration);
            bar.setString(this.duration + " s");
            bar.setStringPainted(true);
        }
    }

    public void pause() {
        for (Note n : notes) {
            n.pause();
        }
    }
    
    public void play() {
        getPlayer().getManagedPlayer().resume();
    }

    public void initValues() {
        this.bar.setValue(0);
        this.bar.setString("0 s");
        this.bar.setStringPainted(true);
        this.bar.setMaximum(this.duration);
    }

    public Player getPlayer() {
        return notes.get(0).getPlayer();
    }
}
