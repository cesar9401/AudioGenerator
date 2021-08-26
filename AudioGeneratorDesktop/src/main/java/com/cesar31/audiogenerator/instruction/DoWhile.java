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

    private Token info;

    private Token token;
    private List<Instruction> instructions;
    private Operation condition;

    public DoWhile(Token info, Token token, List<Instruction> instructions, Operation condition) {
        this.info = info;
        this.token = token;
        this.instructions = instructions;
        this.condition = condition;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {

        Variable tmp = this.condition.run(table, handler);
        Boolean value;// = tmp != null ? handler.getOperation().getValue(tmp, "hacer-mientras", token) : false;
        if (tmp == null) {
            Err e = new Err(Err.TypeErr.SINTACTICO, token.getLine(), token.getColumn(), token.getValue());
            String description = "No es posible evaluar condicion para la sentencia `" + token.getValue() + "` esto a que probablemente la expresion de condicion no tiene un valor definido o uno de los operadores no tiene valor definido.";
            e.setDescription(description);
            handler.getErrors().add(e);
        }

        Object o = null;
        do {
            SymbolTable local = new SymbolTable(table);
            for (Instruction i : this.instructions) {
                o = i.run(local, handler);
                if (o != null) {
                    if (o instanceof Continue) {
                        break;
                    }

                    if (o instanceof Exit) {
                        return null;
                    }
                }
            }

            // Evaluar condicion do-while
            tmp = this.condition.run(local, handler);
            value = tmp != null ? handler.getOperation().getValue(tmp, "hacer-mientras", token) : false;

            if (o != null) {
                if (o instanceof Continue) {
                    continue;
                }
            }
        } while (value);

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        Variable tmp = this.condition.run(table, handler);
        Boolean value;// = tmp != null ? handler.getOperation().getValue(tmp, "hacer-mientras", token) : false;
        if (tmp == null) {
            Err e = new Err(Err.TypeErr.SINTACTICO, token.getLine(), token.getColumn(), token.getValue());
            String description = "No es posible evaluar condicion para la sentencia `" + token.getValue() + "` esto a que probablemente la expresion de condicion no tiene un valor definido o uno de los operadores no tiene valor definido.";
            e.setDescription(description);
            handler.getErrors().add(e);
        }

        value = tmp != null ? handler.getOperation().getValue(tmp, "hacer-mientras", token) : false;

        SymbolTable local = new SymbolTable(table);
        for (Instruction i : this.instructions) {
            i.test(local, handler);
        }

        return null;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public Operation getCondition() {
        return condition;
    }

    @Override
    public Token getInfo() {
        return this.info;
    }
}
