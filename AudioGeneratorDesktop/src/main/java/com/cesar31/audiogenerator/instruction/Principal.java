package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
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
        SymbolTable local = new SymbolTable(table);
        for (Instruction i : instructions) {
            // System.out.println(i.getClass().getSimpleName());
            i.run(local, handler);
        }
        return null;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public Token getInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        SymbolTable local = new SymbolTable(table);
        for (Instruction i : instructions) {
            i.test(local, handler);
        }

        return null;
    }

    public String getFunctionId() {
        String functionId = "principal()";
        return functionId;
    }
}
