package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Case implements Serializable {

    private Token token;
    private Operation operation;
    private List<Instruction> instructions;

    public Case(Token token, Operation operation, List<Instruction> instructions) {
        this.token = token;
        this.operation = operation;
        this.instructions = instructions;
    }

    public Token getToken() {
        return token;
    }

    public Operation getOperation() {
        return operation;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}
