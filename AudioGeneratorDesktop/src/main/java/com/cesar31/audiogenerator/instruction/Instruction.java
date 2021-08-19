package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;

/**
 *
 * @author cesar31
 */
public interface Instruction {

    public Object run(SymbolTable table, OperationHandler handler);

    public Integer getTab();

    public void setTab(Integer tab);
}
