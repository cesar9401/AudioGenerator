package com.cesar31.audiogenerator.instruction;

/**
 *
 * @author cesar31
 */
public interface Instruction {

    public Object run(SymbolTable table);

    public Integer getTab();

    public void setTab(Integer tab);

    public void sayName();
}
