package com.cesar31.audiogenerator.instruction;

import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public enum Var implements Serializable {
    STRING("cadena"),
    DOUBLE("doble"),
    INTEGER("entero"),
    CHAR("caracter"),
    BOOLEAN("boolean"),
    VOID("void");

    private final String name;

    private Var(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
