package com.cesar31.audiogenerator.control;

import org.jfugue.player.Player;

/**
 *
 * @author csart
 */
public class Position extends Thread {

    private Player player;

    public Position(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        while (player.getManagedPlayer().isPlaying()) {
            try {
                long time = (long) (this.player.getManagedPlayer().getTickPosition() / (double) this.player.getManagedPlayer().getTickLength() * 100d);
                System.out.println(time);
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
}
