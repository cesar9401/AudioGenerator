package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class DoWhile implements Instruction {

    private Token token;

    private List<Instruction> instructions;
    private Operation condition;

    public DoWhile(Token token, List<Instruction> instructions, Operation condition) {
        this.token = token;
        this.instructions = instructions;
        this.condition = condition;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {

        Variable tmp = this.condition.run(table, handler);
        Boolean value;
        if (tmp == null) {
            Err e = new Err(Err.TypeErr.SINTACTICO, token.getLine(), token.getColumn(), token.getValue());
            String description = "No es posible evaluar condicion para la sentencia `" + token.getValue() + "` esto a que probablemente la expresion de condicion no tiene un valor definido o uno de los operadores no tiene valor definido.";
            e.setDescription(description);
            handler.getErrors().add(e);
        }

        do {
            SymbolTable local = new SymbolTable(table);
            for (Instruction i : this.instructions) {
                i.run(local, handler);
            }

            tmp = this.condition.run(local, handler);
            value = tmp != null ? handler.getOperation().getValue(tmp, "hacer-mientras", token) : false;
        } while (value);

        return null;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
    }
}
