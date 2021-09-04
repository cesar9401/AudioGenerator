package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public class Continue implements Instruction, Serializable {

    Token token;

    public Continue(Token token) {
        this.token = token;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        return this;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public Token getInfo() {
        return this.token;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }
}
