package com.cesar31.audiogenerator.instruction;

import java.util.List;

/**
 *
 * @author cesar31
 */
public class IfInstruction implements Instruction {

    private List<If> instructions;

    public IfInstruction() {
    }

    public IfInstruction(List<If> instructions) {
        this.instructions = instructions;
    }

    @Override
    public void sayName() {
        System.out.println("if-instruction");
    }

    @Override
    public Object run(SymbolTable table) {
        return null;
    }

    public List<If> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<If> instructions) {
        this.instructions = instructions;
    }
}
