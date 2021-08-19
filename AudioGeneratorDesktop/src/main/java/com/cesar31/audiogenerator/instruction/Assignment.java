package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Assignment implements Instruction {

    private Integer tab;

    private boolean keep;
    private Token type;
    private Token id;
    private Operation value;

    public Assignment() {
        this.tab = 0;
    }

    // Declaracion y asignacion
    public Assignment(Integer tab, boolean keep, Token type, Token id, Operation value) {
        this();
        this.tab = tab;
        this.keep = keep;
        this.type = type;
        this.id = id;
        this.value = value;
    }

    // Asignacion
    public Assignment(Token id, Operation value) {
        this();
        this.id = id;
        this.value = value;
    }

    // para declaracion y asignacion ciclo for
    public Assignment(Token type, Token id, Operation value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }

    @Override
    public void setTab(Integer tab) {
        this.tab = tab;
    }

    @Override
    public Integer getTab() {
        return this.tab;
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
            Variable v = value.run(table, handler);
            handler.getEnvironment().makeAssignment(id, v, table);
        }
        return null;
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
