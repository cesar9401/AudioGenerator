package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Assignment implements Instruction {

    private Token info;

    private TypeA kindA;

    private boolean keep;
    private Token type;
    private Token id;
    private Operation value;

    public enum TypeA {
        EQUAL, PLUS_EQ, PLUS_PLUS, MINUS_MINUS
    }

    public Assignment() {
    }

    // Declaracion y asignacion
    public Assignment(Token info, boolean keep, Token type, Token id, Operation value) {
        this.info = info;
        this.keep = keep;
        this.type = type;
        this.id = id;
        this.value = value;
    }

    // Asignacion
    public Assignment(TypeA kindA, Token id, Operation value) {
        this.kindA = kindA;
        this.id = id;
        this.info = id;
        this.value = value;
    }

    // para declaracion y asignacion ciclo for
    public Assignment(Token type, Token id, Operation value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        // Declaracion
        if (this.type != null) {
            // Declaracion y asignacion
            if (value != null) {
                Variable v = value.run(table, handler);
                handler.getEnvironment().addSymbolTable(type, id, v, table, keep, true);
            } else {
                // Solo declaracion
                handler.getEnvironment().addSymbolTable(type, id, null, table, keep, false);
            }
        } else if (id != null && value != null) {
            // Solo asignacion
            //Variable v = value.run(table, handler);
            handler.getEnvironment().makeAssignment(kindA, id, value, table);
        }
        return null;
    }

    @Override
    public Token getInfo() {
        return this.info;
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
