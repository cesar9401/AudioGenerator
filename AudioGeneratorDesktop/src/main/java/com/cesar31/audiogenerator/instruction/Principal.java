package com.cesar31.audiogenerator.instruction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Principal implements Instruction, Ins {

    private Integer tab;
    private List<Instruction> instructions;

    public Principal() {
        this.tab = 0;
        this.instructions = new ArrayList<>();
    }

    public Principal(Integer tab) {
        this();
        this.tab = tab;
    }

    @Override
    public Object run(SymbolTable table) {
        //Crear SymbolTable local
        System.out.println("run principal -> " + instructions.size());
        System.out.println("");
        SymbolTable local = new SymbolTable(table);
        for (Instruction i : instructions) {
            //System.out.println(i.getClass().getSimpleName());
            i.run(local);
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

    @Override
    public void sayName() {
        System.out.println("principal");
    }

    @Override
    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public List<Instruction> getInstructions() {
        return this.instructions;
    }

    @Override
    public boolean isInAst() {
        return true;
    }
}
