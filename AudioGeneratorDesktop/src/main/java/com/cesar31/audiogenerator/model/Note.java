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
        this.player.play(this.pattern);
    }
    
    
    public void play() {
        this.player.play(this.pattern);
    }
}
