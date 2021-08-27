package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Parameter implements Instruction {

    private Token type;
    private Token id;
    
    private Variable value;

    public Parameter(Token type, Token id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        handler.getEnvironment().addSymbolTable(type, id, value, table, false, true);
        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }

    @Override
    public Token getInfo() {
        return this.type;
    }

    public Token getType() {
        return type;
    }

    public Token getId() {
        return id;
    }

    public void setValue(Variable value) {
        this.value = value;
    }
}
