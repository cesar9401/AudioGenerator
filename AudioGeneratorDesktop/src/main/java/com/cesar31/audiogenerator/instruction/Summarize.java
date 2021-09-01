package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Summarize extends Function {

    private Token info;

    public Summarize(Token info) {
        this.info = info;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        Variable val = super.getValues().get(0);

        if (val.getDimensions() != null) {
            if (val.getDimensions().length == 1) {
                return handler.getNativeF().getSummarize(val, this.info);
            } else {
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), val.getId());
                String description = "En la llamada a la funcion sumarizar, el arreglo `" + val.getId() + "` tiene mas de una dimension, no es posible evaluar la funcion.";
                err.setDescription(description);
                handler.getErrors().add(err);
                return null;
            }
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return new Variable(Var.STRING, "");
    }
}
