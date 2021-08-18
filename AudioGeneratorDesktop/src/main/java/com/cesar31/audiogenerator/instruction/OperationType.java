package com.cesar31.audiogenerator.instruction;

/**
 *
 * @author cesar31
 */
public enum OperationType {
    // tipos de variables
    INTEGER,
    DOUBLE,
    STRING,
    CHAR,
    BOOLEAN,
    ID,
    ARRAY_ACCESS,
    
    // relacionales
    EQEQ,
    NEQ,
    NULL,
    SMALLER,
    GREATER,
    LESS_OR_EQUAL,
    GREATER_OR_EQUAL,
    
    // logicas
    AND,
    NAND,
    OR,
    NOR,
    XOR,
    NOT,
    
    // aritmeticas
    SUM,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    UMINUS,
    POW,
    MOD
}
                