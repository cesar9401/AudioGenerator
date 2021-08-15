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
        SymbolTable tmp = this;
        while (tmp != null) {
            for (Variable v : tmp) {
                if (v.getId().equals(id)) {
                    return v;
                }
            }
            tmp = tmp.getFather();
        }

        return null;
    }

    public boolean containsVariable(String id) {
        SymbolTable tmp = this;
        while (tmp != null) {
            for (Variable v : tmp) {
                if (v.getId().equals(id)) {
                    //System.out.println(v);
                    return true;
                }
            }
            tmp = tmp.getFather();
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
