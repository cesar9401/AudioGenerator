package com.cesar31.audiogenerator.instruction;

import java.util.ArrayList;

/**
 *
 * @author cesar31
 */
public class SymbolTable extends ArrayList<Variable> {

    private SymbolTable father;
    
    public SymbolTable() {
        super();
    }

    public SymbolTable(SymbolTable father) {
        super();
        this.father = father;
    }

    public Variable getVariable(String id) {
        for (Variable v : this) {
            if (v.getId().equals(id)) {
                return v;
            }
        }
        return null;
    }

    public boolean containsVariable(String id) {
        for (Variable v : this) {
            if (v.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public SymbolTable getFather() {
        return father;
    }

    public void setFather(SymbolTable father) {
        this.father = father;
    }
}
