package com.cesar31.audiogenerator.instruction;

import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public enum OperationType implements Serializable {
    // tipos de variables
    INTEGER,
    DOUBLE,
    STRING,
    CHAR,
    BOOLEAN,
    ID,
    ARRAY_ACCESS,
    
    // llamada a funcion
    FUNCTION_CALL,
    
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
                