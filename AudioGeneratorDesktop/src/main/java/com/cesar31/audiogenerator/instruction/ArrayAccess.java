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
    private List<Operation> indexes;

    public ArrayAccess(Token id, List<Operation> indexes) {
        this.id = id;
        this.indexes = indexes;
    }

    @Override
    public Variable run(SymbolTable table, OperationHandler handler) {
        return handler.getArray().getItemFromArray(id, indexes, table, handler);
    }

    @Override
    public Integer getTab() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTab(Integer tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
