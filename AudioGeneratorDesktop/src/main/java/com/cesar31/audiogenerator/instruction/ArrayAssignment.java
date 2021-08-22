package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;

/**
 *
 * @author cesar31
 */
public class ArrayAssignment extends Assignment implements Instruction {

    private ArrayAccess arrayItem;
    private Operation operation;

    public ArrayAssignment() {
    }

    public ArrayAssignment(ArrayAccess arrayItem, Operation operation) {
        this.arrayItem = arrayItem;
        this.operation = operation;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {

        return null;
    }
}
