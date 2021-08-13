package com.cesar31.audiogenerator.instruction;

import java.util.List;

/**
 *
 * @author cesar31
 */
public class While implements Instruction {

    private Operation condition;
    private List<Instruction> instructions;

    public While() {
    }

    public While(Operation condition, List<Instruction> instructions) {
        this.condition = condition;
        this.instructions = instructions;
    }

    @Override
    public void sayName() {
        System.out.println("while");
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

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
