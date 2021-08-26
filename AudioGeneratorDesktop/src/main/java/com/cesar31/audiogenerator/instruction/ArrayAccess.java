package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ArrayAccess implements Instruction {

    private Token id;
    private List<ArrayIndex> indexes;

    public ArrayAccess(Token id, List<ArrayIndex> indexes) {
        this.id = id;
        this.indexes = indexes;
    }

    @Override
    public Variable run(SymbolTable table, OperationHandler handler) {
        return handler.getArray().getItemFromArray(id, indexes, table);
    }

    public Token getId() {
        return id;
    }

    public List<ArrayIndex> getIndexes() {
        return indexes;
    }

    @Override
    public Token getInfo() {
        return this.id;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }
}
