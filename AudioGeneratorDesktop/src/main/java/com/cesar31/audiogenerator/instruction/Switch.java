package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Switch implements Instruction {

    Token token;
    Operation value;
    List<Case> instructions;

    public Switch(Token token, Operation value, List<Case> instructions) {
        this.token = token;
        this.value = value;
        this.instructions = instructions;
    }
    
    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        return null;
    }

    public Token getToken() {
        return token;
    }

    public Operation getValue() {
        return value;
    }

    public List<Case> getInstructions() {
        return instructions;
    }
}
