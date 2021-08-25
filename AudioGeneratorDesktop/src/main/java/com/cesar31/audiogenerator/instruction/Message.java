package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Message implements Instruction {

    private Token info;
    private Operation operation;

    public Message(Token info, Operation operation) {
        this.info = info;
        this.operation = operation;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        Variable v = operation.run(table, handler);
        if (v != null) {
            if (v.getValue() != null) {
                System.out.println(v.getValue());
            } else {
                // variable no tiene valor definido, no es posible emitir mensaje
            }
        } else {
            // No es posible emitir mensaje
        }

        return null;
    }

    @Override
    public Token getInfo() {
        return this.info;
    }
}
