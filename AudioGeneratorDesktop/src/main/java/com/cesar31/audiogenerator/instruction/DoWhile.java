package com.cesar31.audiogenerator.instruction;

import java.util.List;

/**
 *
 * @author cesar31
 */
public class DoWhile implements Instruction {

    private List<Instruction> instructions;
    private Operation condition;

    public DoWhile() {
    }

    public DoWhile(List<Instruction> instructions, Operation condition) {
        this.instructions = instructions;
        this.condition = condition;
    }

    @Override
    public void sayName() {
        System.out.println("do-while");
    }

    @Override
    public Object run(SymbolTable table) {
        return null;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
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
