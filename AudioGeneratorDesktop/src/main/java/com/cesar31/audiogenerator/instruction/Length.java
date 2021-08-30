package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Length extends Function {

    private Token info;
    
    public Length(Token info) {
        this.info = info;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        Variable val = super.getValues().get(0);
        
        if (val.getArray() != null) {
            if (val.getDimensions() != null) {
                return new Variable(Var.INTEGER, String.valueOf(val.getDimensions()[0]));
            }
            return null;
        }

        if (val.getType() == Var.STRING) {
            if (val.getValue() != null) {
                return new Variable(Var.INTEGER, String.valueOf(val.getValue().length()));
            } else {
                System.out.println("variable no tiene valor definido");
            }
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }

    @Override
    public String getFunctionId() {
        String functionId = "";
        return functionId;
    }
}
