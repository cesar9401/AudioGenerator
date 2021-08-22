package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class IfInstruction implements Instruction {

    private List<If> if_instructions;

    public IfInstruction() {
        this.if_instructions = new ArrayList<>();
    }

    public IfInstruction(If if_) {
        this();
        this.if_instructions.add(if_);
    }

    public IfInstruction(If if_, If else_) {
        this(if_);
        this.if_instructions.add(else_);
    }

    public IfInstruction(If if_, List<If> if_else) {
        this(if_);
        this.if_instructions.addAll(if_else);
    }

    public IfInstruction(If if_, List<If> if_else, If else_) {
        this(if_, if_else);
        this.if_instructions.add(else_);
    }

    public If.Type getCurrent() {
        return If.Type.IF;
    }

    public void setCurrent(If.Type current) {
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        for (If i : if_instructions) {

            Operation operation = i.getCondition();
            Boolean value = true;
            boolean error = false;
            
            if(operation == null) {
                value = true;
            } else {
                Variable v = operation.run(table, handler);
                if(v != null) {
                    value = handler.getOperation().getValue(v);
                } else {
                    error = true;
                    System.out.println("value no es de tipo boolean");
                }
            }
            
            // Boolean value = operation != null ? handler.getOperation().getValue(v) : true;
            if(!error) {
                if (value) {
                    SymbolTable local = new SymbolTable(table);
                    for (Instruction j : i.getInstructions()) {
                        j.run(local, handler);
                    }
                    return null;
                }
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
        return 0;
    }

    @Override
    public void setTab(Integer tab) {
    }
}
