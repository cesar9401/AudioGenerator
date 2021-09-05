package com.cesar31.audiogenerator.playlist;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author csart
 */
public class Playlist implements Serializable {

    private String name;
    private boolean random;
    private boolean circular;
    private List<String> playlist;

    public Playlist() {
    }

    public Playlist(String name, boolean random, boolean circular, List<String> playlist) {
        this.name = name;
        this.random = random;
        this.circular = circular;
        this.playlist = playlist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public boolean isCircular() {
        return circular;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Playlist other = (Playlist) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Playlist{" + "name=" + name + ", random=" + random + ", circular=" + circular + ", playlist=" + playlist + '}';
    }
}
