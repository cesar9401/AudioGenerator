package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public interface Instruction {

    public Object run(SymbolTable table, OperationHandler handler);
    
    public Token getInfo();
}
