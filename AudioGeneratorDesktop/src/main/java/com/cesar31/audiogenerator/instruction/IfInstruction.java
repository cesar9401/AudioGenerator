package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
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

            if (operation == null) {
                value = true;
            } else {
                Variable v = operation.run(table, handler);
                if (v != null) {
                    // Error no es de tipo boolean se verifica al obtener valor
                    value = handler.getOperation().getValue(v, i.getType().getName(), i.getToken());
                } else {
                    Err e = new Err(Err.TypeErr.SINTACTICO, i.getToken().getLine(), i.getToken().getColumn(), i.getType().getName());
                    String description = "No es posible evaluar condicion para la sentencia `" + i.getType().getName() + "` esto a que probablemente la expresion de condicion no tiene un valor definido o uno de los operadores no tiene valor definido.";
                    e.setDescription(description);
                    handler.getErrors().add(e);
                    value = false;
                }
            }

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
}
