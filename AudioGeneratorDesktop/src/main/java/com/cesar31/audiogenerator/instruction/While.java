package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class While implements Instruction {

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

        while (value) {
            SymbolTable local = new SymbolTable(table);
            for (Instruction i : instructions) {
                i.run(local, handler);
            }
            v = this.condition.run(local, handler);
            value = v != null ? handler.getOperation().getValue(v, "mientras", token) : false;
        }

        return null;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
    }
}
