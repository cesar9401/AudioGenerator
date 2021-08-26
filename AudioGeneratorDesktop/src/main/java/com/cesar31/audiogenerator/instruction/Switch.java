package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Switch implements Instruction {

    Token token;
    Operation value;
    List<Case> instructions;

    public Switch(Token token, Operation value, List<Case> instructions) {
        this.token = token;
        this.value = value;
        this.instructions = instructions;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        Variable v = value.run(table, handler);
        if (v != null) {
            if (instructions != null) {
                for (Case c : instructions) {
                    SymbolTable local = new SymbolTable(table);

                    Operation tmp = c.getOperation();
                    Boolean value = true;
                    Object o = null;
                    if (tmp != null) {
                        Variable val = tmp.run(local, handler);
                        value = val.getValue().equals(v.getValue());
                    } else {
                        value = true;
                    }

                    if (value) {
                        for (Instruction i : c.getInstructions()) {
                            o = i.run(local, handler);

                            if (o != null) {
                                if (o instanceof Exit) {
                                    return null;
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("No hay case's en switch xd");
            }
        } else {
            System.out.println("No es posible evaluar switch");
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {

        Variable v = value.test(table, handler);
        if (v != null) {
            for (Case c : instructions) {
                SymbolTable local = new SymbolTable(table);
                Operation tmp = c.getOperation();
                if (tmp != null) {
                    Variable value = tmp.test(local, handler);
                    if (value != null) {
                        if (value.getType() != value.getType()) {
                            System.out.println("No son del mismo tipo");
                        }
                    } else {
                        System.out.println("No es posible evaluar case");
                    }
                }
                
                for(Instruction i : c.getInstructions()) {
                    i.test(local, handler);
                }

            }
        } else {
            // error se verifica en value.test(table, handler);
            Err err = new Err(Err.TypeErr.SINTACTICO, token.getLine(), token.getColumn(), "");
            String description = "No es posible evaluar switch, debido a que la variable a evaluar, no tiene un valor definido.";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }

    public Token getToken() {
        return token;
    }

    public Operation getValue() {
        return value;
    }

    public List<Case> getInstructions() {
        return instructions;
    }

    @Override
    public Token getInfo() {
        return this.token;
    }
}
