package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public class ArrayAssignment extends Assignment implements Instruction, Serializable {

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
        //System.out.println(arrayItem.getInfo());
        handler.getArray().assignElementToArray(arrayItem, operation, type, table);

        return null;
    }

    @Override
    public Token getInfo() {
        return arrayItem.getInfo();
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }
}
