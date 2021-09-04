package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class While implements Instruction, Serializable {

    private Token token;

    private Operation condition;
    private List<Instruction> instructions;

    public While(Token token, Operation condition, List<Instruction> instructions) {
        this.token = token;
        this.condition = condition;
        this.instructions = instructions;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {

        Boolean value = true;
        Variable v = this.condition.run(table, handler);
        if (v != null) {
            value = handler.getOperation().getValue(v, "mientras", token);
        } else {
            Err e = new Err(Err.TypeErr.SINTACTICO, token.getLine(), token.getColumn(), token.getValue());
            String description = "No es posible evaluar condicion para la sentencia `" + token.getValue() + "` esto a que probablemente la expresion de condicion no tiene un valor definido o uno de los operadores no tiene valor definido.";
            e.setDescription(description);
            handler.getErrors().add(e);
            value = false;
        }

        Object o = null;
        while (value) {
            SymbolTable local = new SymbolTable(table);
            for (Instruction i : instructions) {
                o = i.run(local, handler);
                if (o != null) {
                    // Continue
                    if (o instanceof Continue) {
                        break;
                    }

                    if (o instanceof Exit) {
                        return null;
                    }

                    // Return
                    if (o instanceof Return) {
                        return o;
                    }
                }
            }

            v = this.condition.run(local, handler);
            value = v != null ? handler.getOperation().getValue(v, "mientras", token) : false;
            if (o != null) {
                // Continue
                if (o instanceof Continue) {

                    continue;
                }
            }
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {

        Boolean value = true;
        Variable v = this.condition.test(table, handler);
        if (v != null) {
            value = handler.getOperation().getValue(v, "mientras", token);
        } else {
            Err e = new Err(Err.TypeErr.SINTACTICO, token.getLine(), token.getColumn(), token.getValue());
            String description = "No es posible evaluar condicion para la sentencia `" + token.getValue() + "` esto a que probablemente la expresion de condicion no tiene un valor definido o uno de los operadores no tiene valor definido.";
            e.setDescription(description);
            handler.getErrors().add(e);
            value = false;
        }

        SymbolTable local = new SymbolTable(table);

        for (Instruction i : this.instructions) {
            i.test(local, handler);
        }

        return null;
    }

    public Operation getCondition() {
        return condition;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public Token getInfo() {
        return this.token;
    }
}
