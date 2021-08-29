package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

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

    @Override
    public Token getInfo() {
        return arrayItem.getInfo();
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        if(handler.isTest()) {
            return null;
        }
        
        return this.run(table, handler);
    }
}
