package com.cesar31.audiogenerator.model;

import com.cesar31.audiogenerator.instruction.Instruction;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author cesar31
 */
public class Track implements Serializable {

    private String name;
    private List<Instruction> ast;
    private List<Sound> sounds;
    private double duration;
    private String source;

    public Track() {

    }

    public Track(String name, List<Instruction> ast) {
        this.ast = ast;
    }

    public Track(String name, List<Instruction> ast, List<Sound> sounds, double duration, String source) {
        this.name = name;
        this.ast = ast;
        this.sounds = sounds;
        this.duration = duration;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Instruction> getAst() {
        return ast;
    }

    public void setAst(List<Instruction> ast) {
        this.ast = ast;
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void setSounds(List<Sound> sounds) {
        this.sounds = sounds;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Track other = (Track) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Track{" + "name=" + name + ", duration=" + duration + '}';
    }
}
