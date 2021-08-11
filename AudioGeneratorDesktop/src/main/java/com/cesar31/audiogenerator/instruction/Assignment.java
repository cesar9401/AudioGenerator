package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Assignment implements Instruction {

    private boolean keep;
    private Token type;
    private Token id;
    private Operation value;

    public Assignment() {
    }

    // Declaracion y asignacion
    public Assignment(boolean keep, Token type, Token id, Operation value) {
        this.keep = keep;
        this.type = type;
        this.id = id;
        this.value = value;
    }

    // Asignacion
    public Assignment(Token id, Operation value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Object run(SymbolTable table) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public Token getType() {
        return type;
    }

    public void setType(Token type) {
        this.type = type;
    }

    public Token getId() {
        return id;
    }

    public void setId(Token id) {
        this.id = id;
    }

    public Operation getValue() {
        return value;
    }

    public void setValue(Operation value) {
        this.value = value;
    }
}
