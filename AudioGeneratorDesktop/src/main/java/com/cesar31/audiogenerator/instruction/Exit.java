package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public class Exit implements Instruction, Serializable {

    private Token token;

    public Exit(Token token) {
        this.token = token;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        return this;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }

    @Override
    public Token getInfo() {
        return this.token;
    }

    public Token getToken() {
        return token;
    }
}
