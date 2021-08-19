package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
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
    public Object run(SymbolTable table, OperationHandler handler) {
        SymbolTable local = new SymbolTable(table);
        this.init.run(local, handler);
        while(handler.getOperation().getValue(condition.run(local, handler))) {
            SymbolTable local1 = new SymbolTable(local);
            for(Instruction i : instructions) {
                i.run(local1, handler);
            }
            increase.run(local1, handler);
        }
        
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
