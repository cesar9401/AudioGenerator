package com.cesar31.audiogenerator.playlist;

import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author csart
 */
public class TrackList implements MusicList {

    private Token info;
    private List<Token> list;

    public TrackList(Token info, List<Token> list) {
        this.info = info;
        this.list = list;
    }

    @Override
    public Token getInfo() {
        return this.info;
    }

    public List<Token> getList() {
        return list;
    }
}
