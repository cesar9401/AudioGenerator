package com.cesar31.audiogenerator.instruction;

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
    public Object run(SymbolTable table) {
        Variable v = operation.run(table);
        System.out.println(v.getValue());
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

    @Override
    public void sayName() {
        System.out.println("Mensaje");
    }

}
