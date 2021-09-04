package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public class Return implements Instruction, Serializable {

    private Token info;
    private Operation operation;

    public Return(Token info, Operation operation) {
        this.info = info;
        this.operation = operation;
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
        return this.info;
    }

    public Operation getOperation() {
        return operation;
    }
}
