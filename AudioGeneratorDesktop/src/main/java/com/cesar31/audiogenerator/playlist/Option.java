package com.cesar31.audiogenerator.playlist;

import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author csart
 */
public class Option implements MusicList {

    public enum Type {
        CIRCULAR,
        RANDOM
    }

    private Type type;
    private Token info;
    private boolean value;

    public Option(Token info, Type type, boolean value) {
        this.info = info;
        this.type = type;
        this.value = value;
    }

    @Override
    public Token getInfo() {
        return this.info;
    }

    public Type getType() {
        return type;
    }

    public boolean isValue() {
        return value;
    }
}
