package com.cesar31.audiogenerator.instruction;

import java.util.List;

/**
 *
 * @author cesar31
 */
public class For implements Instruction, Ins {

    private Integer tab;

    private Assignment init;
    private Operation condition;
    private Assignment increase;
    private List<Instruction> instructions;

    public For() {
        this.tab = 0;
    }

    public For(Assignment init, Operation condition, Assignment increase) {
        this();
        this.init = init;
        this.condition = condition;
        this.increase = increase;
    }

    public For(Assignment init, Operation condition, Assignment increase, List<Instruction> instructions) {
        this();
        this.init = init;
        this.condition = condition;
        this.increase = increase;
        this.instructions = instructions;
    }

    @Override
    public void sayName() {
        System.out.println("for");
    }

    @Override
    public Object run(SymbolTable table) {
        return null;
    }

    public Assignment getInit() {
        return init;
    }

    public void setInit(Assignment init) {
        this.init = init;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
    }

    public Assignment getIncrease() {
        return increase;
    }

    public void setIncrease(Assignment increase) {
        this.increase = increase;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTab(Integer tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
