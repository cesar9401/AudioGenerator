package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.EnvironmentHandler;
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

    @Override
    public void setTab(Integer tab) {
        this.tab = tab;
    }

    @Override
    public Integer getTab() {
        return this.tab;
    }

    @Override
    public void sayName() {
        System.out.println("Assignment");
    }

    @Override
    public Object run(SymbolTable table) {
        // Declaracion
        if (this.type != null) {
            EnvironmentHandler handler = new EnvironmentHandler();
            // Declaracion y asignacion
            if (value != null) {
                Variable v = value.run(table);
                handler.addSymbolTable(type, id, v, table, keep, true);
            } else {
                // Solo declaracion
                handler.addSymbolTable(type, id, null, table, keep, false);
            }
        } else if (id != null && value != null) {
            // Solo asignacion

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