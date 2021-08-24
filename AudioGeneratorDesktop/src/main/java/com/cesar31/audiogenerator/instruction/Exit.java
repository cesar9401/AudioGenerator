package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Exit implements Instruction {

    private Token token;

    public Exit(Token token) {
        this.token = token;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        return this;
    }

    public Token getToken() {
        return token;
    }
}
