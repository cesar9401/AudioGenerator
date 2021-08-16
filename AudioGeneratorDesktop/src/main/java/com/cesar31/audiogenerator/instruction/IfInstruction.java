package com.cesar31.audiogenerator.instruction;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class IfInstruction implements Instruction {

    private Integer tab;
    private List<If> if_instructions;
    private If.Type current;

    public IfInstruction() {
        this.tab = 0;
        this.if_instructions = new ArrayList<>();
    }

    public IfInstruction(Integer tab, If.Type current) {
        this();
        this.tab = tab;
        this.current = current;
    }

    public If.Type getCurrent() {
        return current;
    }

    public void setCurrent(If.Type current) {
        this.current = current;
    }

    @Override
    public void sayName() {
        System.out.println(current.toString());
    }

    @Override
    public Object run(SymbolTable table) {
        for (If i : if_instructions) {

            Operation operation = i.getCondition();
            Boolean value = operation != null ? getValue(operation.run(table)) : true;
            
            if (value) {
                SymbolTable local = new SymbolTable(table);
                for (Instruction j : i.getInstructions()) {
                    j.run(local);
                }
                return null;
            }
        }

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

    public List<If> getIf_instructions() {
        return if_instructions;
    }

    public void setIf_instructions(List<If> if_instructions) {
        this.if_instructions = if_instructions;
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
