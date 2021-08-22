package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class For implements Instruction {

    private Token token;

    private Assignment init;
    private Operation condition;
    private Assignment increase;
    private List<Instruction> instructions;

    public For(Token token, Assignment init, Operation condition, Assignment increase, List<Instruction> instructions) {
        this.token = token;
        this.init = init;
        this.condition = condition;
        this.increase = increase;
        this.instructions = instructions;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        SymbolTable local = new SymbolTable(table);
        this.init.run(local, handler);

        Boolean value = true;
        Variable v = this.condition.run(local, handler);
        if (v != null) {
            value = handler.getOperation().getValue(v, "para", token);
        } else {
            Err e = new Err(Err.TypeErr.SINTACTICO, token.getLine(), token.getColumn(), token.getValue());
            String description = "No es posible evaluar condicion para la sentencia `" + token.getValue() + "` esto a que probablemente la expresion de condicion no tiene un valor definido o uno de los operadores no tiene valor definido.";
            e.setDescription(description);
            handler.getErrors().add(e);
            value = false;
        }

        while (value) {
            SymbolTable local1 = new SymbolTable(local);
            for (Instruction i : instructions) {
                i.run(local1, handler);
            }
            increase.run(local1, handler);
            v = this.condition.run(local1, handler);
            value = v != null ? handler.getOperation().getValue(v, "para", token) : false;
        }

        return null;
    }

    public Assignment getInit() {
        return init;
    }

    public void setInit(Assignment init) {
        this.init = init;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
    }

    public Assignment getIncrease() {
        return increase;
    }

    public void setIncrease(Assignment increase) {
        this.increase = increase;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }
}
