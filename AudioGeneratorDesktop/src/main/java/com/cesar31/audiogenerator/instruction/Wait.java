package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public class Wait extends Function implements Serializable {

    private Token info;

    public Wait(Token info) {
        this.info = info;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        return handler.getRender().createWait(info, values.get(0), values.get(1));
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return new Variable(Var.INTEGER, values.get(0).getValue());
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
