package com.cesar31.audiogenerator.request;

import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author csart
 */
public class RequestName implements Request {

    private Token info;
    private Token name;

    public RequestName(Token info, Token name) {
        this.info = info;
        this.name = name;
    }
    
    @Override
    public Token getInfo() {
        return info;
    }

    public Token getName() {
        return name;
    }
}
