package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class DoWhile implements Instruction, Ins {

    private List<Instruction> instructions;
    private Operation condition;

    public DoWhile(List<Instruction> instructions, Operation condition) {
        this.instructions = instructions;
        this.condition = condition;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {

        do {
            SymbolTable local = new SymbolTable(table);
            for (Instruction i : this.instructions) {
                i.run(local, handler);
            }
        } while (handler.getOperation().getValue(this.condition.run(table, handler)));

        return null;
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
