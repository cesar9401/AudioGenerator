package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;

/**
 *
 * @author cesar31
 */
public class Message implements Instruction {

    private Operation operation;

    public Message(Operation operation) {
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
    public Integer getTab() {
        return 0;
    }

    @Override
    public void setTab(Integer tab) {
    }
}
