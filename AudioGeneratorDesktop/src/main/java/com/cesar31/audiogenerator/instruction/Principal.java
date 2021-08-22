package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Principal implements Instruction {

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
}
