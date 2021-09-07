package com.cesar31.audiogenerator.request;

import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author csart
 */
public class Type implements Request{

    private Token info;
    private Token type;

    public Type(Token info, Token type) {
        this.info = info;
        this.type = type;
    }
    
    @Override
    public Token getInfo() {
        return info;
    }

    public Token getType() {
        return type;
    }
}
