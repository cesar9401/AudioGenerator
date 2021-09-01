package com.cesar31.audiogenerator.model;

/**
 *
 * @author cesar31
 */
public class Sound {

    private String note;
    private String eighth;
    private double milliseconds;
    private int channel;

    public Sound(String note, String eighth, double milliseconds, int channel) {
        this.note = note;
        this.eighth = eighth;
        this.milliseconds = milliseconds;
        this.channel = channel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEighth() {
        return eighth;
    }

    public void setEighth(String eighth) {
        this.eighth = eighth;
    }

    public double getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(double milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Sound{" + "note=" + note + ", eighth=" + eighth + ", milliseconds=" + milliseconds + ", channel=" + channel + '}';
    }
}
