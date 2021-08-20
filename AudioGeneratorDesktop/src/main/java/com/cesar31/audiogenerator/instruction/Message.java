package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;

/**
 *
 * @author cesar31
 */
public class Message implements Instruction {

    private Integer tab;
    private Operation operation;

    public Message(Integer tab, Operation operation) {
        this.tab = tab;
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
        return this.tab;
    }

    @Override
    public void setTab(Integer tab) {
        this.tab = tab;
    }
}
