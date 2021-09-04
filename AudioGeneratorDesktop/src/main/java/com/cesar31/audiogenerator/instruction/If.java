package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class If implements Serializable {

    public enum Type implements Serializable {
        IF("si"), ELSE_IF("sino si"), ELSE("sino");

        private final String name;

        private Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    private Token token;

    private Type type;
    private Operation condition;
    private List<Instruction> instructions;

    public If(Token token, Type type, Operation condition, List<Instruction> instructions) {
        this.token = token;
        this.type = type;
        this.condition = condition;
        this.instructions = instructions;
    }

    public Operation getCondition() {
        return condition;
    }

    public Type getType() {
        return type;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public Token getToken() {
        return token;
    }
}
