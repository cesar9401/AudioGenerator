package com.cesar31.audiogenerator.instruction;

/**
 *
 * @author cesar31
 */
public enum Var {
    STRING(-1),
    DOUBLE(0),
    INTEGER(1),
    CHAR(2),
    BOOLEAN(3),
    VOID(null);

    private final Integer level;

    private Var(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return this.level;
    }
}
