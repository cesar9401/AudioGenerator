package com.cesar31.audiogenerator.instruction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class DoWhile implements Instruction, Ins {

    private Integer tab;
    private List<Instruction> instructions;
    private Operation condition;

    public DoWhile() {
        this.tab = 0;
        this.instructions = new ArrayList<>();
    }

    public DoWhile(Integer tab) {
        this();
        this.tab = tab;
    }

    @Override
    public void sayName() {
        System.out.println("do-while");
    }

    @Override
    public Object run(SymbolTable table) {

        do {
            SymbolTable local = new SymbolTable(table);
            for (Instruction i : this.instructions) {
                i.run(local);
            }
        } while (getValue(this.condition.run(table)));

        return null;
    }

    public boolean getValue(Variable cond) {
        if (cond.getType() == Var.BOOLEAN) {
            String value = cond.getValue().toLowerCase();
            return value.equals("true") || value.equals("verdadero") || value.equals("1");
        }

        System.out.println("While condition no BOOLEAN type");
        return false;
    }

    @Override
    public List<Instruction> getInstructions() {
        return instructions;
    }

    @Override
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
