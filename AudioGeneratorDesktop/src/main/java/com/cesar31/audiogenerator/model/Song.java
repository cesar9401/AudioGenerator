package com.cesar31.audiogenerator.model;

import com.cesar31.audiogenerator.control.Position;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfugue.player.Player;

/**
 *
 * @author cesar31
 */
public class Song extends Thread {

    private List<Note> notes;
    private Position pos;

    public Song(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public void run() {
        for (Note n : notes) {
            n.start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace(System.out);
        }
        
        while (getPlayer().getManagedPlayer().isPlaying()) {
            try {
                long time = (long) (getPlayer().getManagedPlayer().getTickPosition() / (double) getPlayer().getManagedPlayer().getTickLength() * 100d);
                System.out.println(time);
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public void pause() {
        for (Note n : notes) {
            n.pause();
        }
    }

    public Player getPlayer() {
        return notes.get(0).getPlayer();
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }
}
