package com.cesar31.audiogenerator.model;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

/**
 *
 * @author cesar31
 */
public class Note extends Thread {

    private Player player;
    private Pattern pattern;

    public Note(Player player, Pattern pattern) {
        this.player = player;
        this.pattern = pattern;
    }

    @Override
    public void run() {
        // this.player.delayPlay(2500, pattern);
        this.player.play(this.pattern);
    }

    public void play() {
        this.player.play(this.pattern);
    }

    public void pause() {
        //this.player.getManagedPlayer().pause();
        // long pos = 6000;
        // long time = pos * this.player.getManagedPlayer().getTickLength() / 12000;
        // this.player.getManagedPlayer().seek(time);

        System.out.println(this.player.getManagedPlayer().getTickPosition());
        System.out.println(this.player.getManagedPlayer().isPaused());
        System.out.println(this.player.getManagedPlayer().isStarted());
        System.out.println(this.player.getManagedPlayer().isFinished());
    }

    public long getPosition() {
        return (long)(this.player.getManagedPlayer().getTickPosition()/(double)this.player.getManagedPlayer().getTickLength() * 100d);
    }
    
    public Player getPlayer() {
        return player;
    }
}
