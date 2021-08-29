package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class IfInstruction implements Instruction {

    private List<If> instructions;

    public IfInstruction() {
        this.instructions = new ArrayList<>();
    }

    public IfInstruction(If if_) {
        this();
        this.instructions.add(if_);
    }

    public IfInstruction(If if_, If else_) {
        this(if_);
        this.instructions.add(else_);
    }

    public IfInstruction(If if_, List<If> if_else) {
        this(if_);
        this.instructions.addAll(if_else);
    }

    public IfInstruction(If if_, List<If> if_else, If else_) {
        this(if_, if_else);
        this.instructions.add(else_);
    }

    public If.Type getCurrent() {
        return If.Type.IF;
    }

    public void setCurrent(If.Type current) {
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        for (If i : instructions) {

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
                    Object o = j.run(local, handler);
                    if (o != null) {
                        if (o instanceof Continue || o instanceof Exit || o instanceof Return) {
                            return o;
                        }
                    }
                }
                return i;
            }
        }

        return null;
    }

    @Override
    public Token getInfo() {
        return this.instructions.get(0).getToken();
    }

    public List<If> getInstructions() {
        return instructions;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {

        for (If i : instructions) {
            Operation cond = i.getCondition();
            boolean value;
            if (cond != null) {
                Variable v = cond.test(table, handler);
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

            SymbolTable local = new SymbolTable(table);
            for (Instruction j : i.getInstructions()) {
                Object o = j.test(local, handler);
//                if(o instanceof Return) {
//                    return o;
//                }
            }
        }

        return null;
    }
}
