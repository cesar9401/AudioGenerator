package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public class ArrayIndex implements Serializable {

    private Token lbracket;
    private Token rbrakcet;
    private Operation operation;

    public ArrayIndex(Token lbracket, Operation operation, Token rbracket) {
        this.lbracket = lbracket;
        this.operation = operation;
        this.rbrakcet = rbracket;
    }

    public Token getLbracket() {
        return lbracket;
    }

    public Token getRbrakcet() {
        return rbrakcet;
    }

    public Operation getOperation() {
        return operation;
    }
}
