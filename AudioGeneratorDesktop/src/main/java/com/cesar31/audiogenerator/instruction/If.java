package com.cesar31.audiogenerator.instruction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class If implements Ins {

    public enum Type {
        IF, ELSE_IF, ELSE
    }

    private Integer tab;
    private Type type;
    private Operation condition;
    private List<Instruction> instructions;

    public If() {
        this.tab = 0;
        this.instructions = new ArrayList<>();
    }

    public If(Integer tab, Type type, Operation condition) {
        this();
        this.tab = tab;
        this.type = type;
        this.condition = condition;
    }

    public If(Type type, Operation condition, List<Instruction> instructions) {
        this();
        this.type = type;
        this.condition = condition;
        this.instructions = instructions;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    @Override
    public boolean isInAst() {
        return false;
    }
}
