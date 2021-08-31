package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Wait extends Function {

    private Token info;

    public Wait(Token info) {
        this.info = info;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        List<Variable> values = super.getValues();
        
        return new Variable(Var.INTEGER, values.get(0).getValue());
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }

    @Override
    public Token getInfo() {
        return this.info;
    }

    @Override
    public String getFunctionId() {
        return "esperar(entero,entero)";
    }
}
