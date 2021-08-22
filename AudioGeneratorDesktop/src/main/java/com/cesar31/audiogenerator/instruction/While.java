package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class While implements Instruction, Ins {

    private Operation condition;
    private List<Instruction> instructions;

    public While(Operation condition, List<Instruction> instructions) {
        this.condition = condition;
        this.instructions = instructions;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        
        while (handler.getOperation().getValue(this.condition.run(table, handler))) {
            SymbolTable local = new SymbolTable(table);
            for (Instruction i : instructions) {
                i.run(local, handler);
            }
        }

        return null;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
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
        return 0;
    }

    @Override
    public void setTab(Integer tab) {
    }

    @Override
    public boolean isInAst() {
        return false;
    }
}
