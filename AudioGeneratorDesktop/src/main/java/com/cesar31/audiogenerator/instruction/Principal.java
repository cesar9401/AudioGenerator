package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Principal implements Instruction, Ins {

    private List<Instruction> instructions;

    public Principal(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        //Crear SymbolTable local
        System.out.println("run principal -> " + instructions.size());
        System.out.println("");
        SymbolTable local = new SymbolTable(table);
        for (Instruction i : instructions) {
            // System.out.println(i.getClass().getSimpleName());
            i.run(local, handler);
        }
        return null;
    }

    @Override
    public Integer getTab() {
        return 0;
    }

    @Override
    public void setTab(Integer tab) {
    }

    @Override
    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public List<Instruction> getInstructions() {
        return this.instructions;
    }

    @Override
    public boolean isInAst() {
        return true;
    }
}
