package com.cesar31.audiogenerator.model;

import java.util.List;

/**
 *
 * @author cesar31
 */
public class Song extends Thread{

    private List<Note> notes;
    
    public Song(List<Note> notes) {
        this.notes = notes;
    }
    
    @Override
    public void run() {
        for(Note n : notes) {
            n.start();
        }
    }
}
