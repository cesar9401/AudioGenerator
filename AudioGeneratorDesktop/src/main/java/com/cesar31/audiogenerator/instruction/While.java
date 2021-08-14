package com.cesar31.audiogenerator.instruction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class While implements Instruction, Ins {

    private Integer tab;
    private Operation condition;
    private List<Instruction> instructions;

    public While() {
        this.tab = 0;
        this.instructions = new ArrayList<>();
    }

    public While(Integer tab, Operation condition) {
        this();
        this.tab = tab;
        this.condition = condition;
    }
    
    public While(Operation condition, List<Instruction> instructions) {
        this();
        this.condition = condition;
        this.instructions = instructions;
    }

    @Override
    public void sayName() {
        System.out.println("while xd");
    }

    @Override
    public Object run(SymbolTable table) {
        return null;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
    }

    @Override
    public List<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public Integer getTab() {
        return this.tab;
    }

    @Override
    public void setTab(Integer tab) {
        this.tab = tab;
    }
}
