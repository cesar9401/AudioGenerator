package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
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
    public Object run(SymbolTable table, OperationHandler handler) {
        for (If i : if_instructions) {

            Operation operation = i.getCondition();
            Boolean value = operation != null ? handler.getOperation().getValue(operation.run(table, handler)) : true;
            
            if (value) {
                SymbolTable local = new SymbolTable(table);
                for (Instruction j : i.getInstructions()) {
                    j.run(local, handler);
                }
                return null;
            }
        }

        return null;
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
