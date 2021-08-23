package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;

/**
 *
 * @author cesar31
 */
public class ArrayAssignment extends Assignment implements Instruction {

    private ArrayAccess arrayItem;
    private Operation operation;
    private TypeA type;

    public ArrayAssignment() {
    }

    public ArrayAssignment(ArrayAccess arrayItem, Operation operation, TypeA type) {
        this.arrayItem = arrayItem;
        this.operation = operation;
        this.type = type;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {

        handler.getArray().assignElementToArray(arrayItem, operation, type, table);

        return null;
    }
}
