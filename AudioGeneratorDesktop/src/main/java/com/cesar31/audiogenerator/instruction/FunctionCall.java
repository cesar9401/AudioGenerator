package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class FunctionCall implements Instruction {

    private Token id;
    private List<Operation> operations;

    public FunctionCall(Token id, List<Operation> operations) {
        this.id = id;
        this.operations = operations;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();

        String functionId = id.getValue() + "(";
        for (int i = 0; i < operations.size(); i++) {
            Variable v = operations.get(i).run(table, handler);

            functionId += v.getType().getName();

            if (i != operations.size() - 1) {
                functionId += ",";
            }
            values.add(v);
        }
        functionId += ")";

        Function f = handler.getFunctions().get(functionId);
        if (f != null) {
            f.setValues(values);
            f.run(table, handler);
        } else {
            System.out.println("function null " + functionId);
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();
        String functionId = id.getValue() + "(";
        for (int i = 0; i < operations.size(); i++) {
            Variable v = operations.get(i).test(table, handler);
            if (v != null) {
                functionId += v.getType().getName();

                if (i != operations.size() - 1) {
                    functionId += ",";
                }
                values.add(v);
            } else {
                // error variable no definida
                System.out.println("Variable no definida en llamada " + id.getValue());
            }
        }
        functionId += ")";

        Function f = handler.getFunctions().get(functionId);
        if (f != null) {
            if (operations.size() == values.size()) {
                f.setValues(values);
                // f.test(table, handler);
            } else {
                // no es posible realizar llamada a funcion
                System.out.println("No es posible llamar a funcion");
            }
        } else {
            System.out.println("funcion no existe");
        }

        return null;
    }

    @Override
    public Token getInfo() {
        return this.id;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
