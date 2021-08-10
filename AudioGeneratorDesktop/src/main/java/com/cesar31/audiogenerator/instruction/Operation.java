package com.cesar31.audiogenerator.instruction;

/**
 *
 * @author cesar31
 */
public class Operation implements Instruction {

    private Variable value;
    private OperationType type;
    private Operation left;
    private Operation right;

    // Operaciones definidas con un valor o id
    public Operation(OperationType type, Variable value) {
        this.type = type;
        this.value = value;
    }

    // Operaciones unarias
    public Operation(OperationType type, Operation left) {
        this.type = type;
        this.left = left;
    }

    // Operaciones con dos operadores
    public Operation(OperationType type, Operation left, Operation right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
