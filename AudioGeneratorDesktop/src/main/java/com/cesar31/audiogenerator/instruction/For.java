package com.cesar31.audiogenerator.instruction;

import java.util.ArrayList;
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
        this.instructions = new ArrayList<>();
    }

    public For(Integer tab, Assignment init, Operation condition, Assignment increase) {
        this();
        this.tab = tab;
        this.init = init;
        this.condition = condition;
        this.increase = increase;
    }

    @Override
    public void sayName() {
        System.out.println("for");
    }

    @Override
    public Object run(SymbolTable table) {
        SymbolTable local = new SymbolTable(table);
        this.init.run(local);
        while(getValue(condition.run(local))) {
            SymbolTable local1 = new SymbolTable(local);
            for(Instruction i : instructions) {
                i.run(local1);
            }
            increase.run(local1);
        }
        
        return null;
    }

    public boolean getValue(Variable cond) {
        if (cond.getType() == Var.BOOLEAN) {
            String value = cond.getValue().toLowerCase();
            return value.equals("true") || value.equals("verdadero") || value.equals("1");
        }

        System.out.println("For condition no BOOLEAN type");
        return false;
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
