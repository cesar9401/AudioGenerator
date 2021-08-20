package com.cesar31.audiogenerator.instruction;

/**
 *
 * @author cesar31
 */
public enum Var {
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
