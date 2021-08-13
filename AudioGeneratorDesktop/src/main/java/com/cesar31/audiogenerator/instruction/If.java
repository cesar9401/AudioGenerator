package com.cesar31.audiogenerator.instruction;

import java.util.List;

/**
 *
 * @author cesar31
 */
public class If {

    private Operation condition;
    private List<Instruction> instructions;

    public If() {
    }

    public If(Operation condition) {
        this.condition = condition;
    }

    public If(Operation condition, List<Instruction> instructions) {
        this.condition = condition;
        this.instructions = instructions;
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
