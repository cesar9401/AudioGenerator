package com.cesar31.audiogenerator.playlist;

import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author csart
 */
public class Name implements MusicList {

    private Token info;
    private String name;

    public Name(Token info, String name) {
        this.info = info;
        this.name = name;
    }

    @Override
    public Token getInfo() {
        return this.info;
    }

    public String getName() {
        return name;
    }
}
