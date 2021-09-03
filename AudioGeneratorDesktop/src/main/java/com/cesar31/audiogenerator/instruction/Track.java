package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.model.Sound;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Track {

    private String name;
    private List<Instruction> ast;
    private List<Sound> sounds;
    private String source;

    public Track() {

    }

    public Track(String name, List<Instruction> ast) {
        this.ast = ast;
    }

    public Track(String name, List<Instruction> ast, List<Sound> sounds, String source) {
        this.name = name;
        this.ast = ast;
        this.sounds = sounds;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public void setSounds(List<Sound> sounds) {
        this.sounds = sounds;
    }
}
