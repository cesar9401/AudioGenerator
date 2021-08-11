package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationMaker;

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
    public Variable run(SymbolTable table) {
        OperationMaker maker = new OperationMaker();
        switch (type) {
            case ID:
                System.out.println("Tabla de simbolos");
                break;
            case INTEGER:
            case STRING:
            case BOOLEAN:
            case CHAR: // revisar longitud
            case DOUBLE: // revisar numero de cifras decimales
                return value;

            //operaciones aritmeticas
            case SUM:
                return maker.sum(left.run(table), right.run(table));
            case SUBTRACTION:
                return maker.subtraction(left.run(table), right.run(table));
            case MULTIPLICATION:
                return maker.multiplication(left.run(table), right.run(table));
            case DIVISION:
                return maker.division(left.run(table), right.run(table));
            case MOD:
                return maker.mod(left.run(table), right.run(table));
            case POW:
                return maker.pow(left.run(table), right.run(table));
            case UMINUS:
                return maker.uminus(left.run(table));

            // operaciones relacionales
            case EQEQ:
            case GREATER:
            case GREATER_OR_EQUAL:
            case LESS_OR_EQUAL:
            case SMALLER:
            case NEQ:
                return maker.compare(left.run(table), right.run(table), type);

            // operaciones logicas
            case AND:
            case NAND:
            case OR:
            case NOR:
            case XOR:
                return maker.logical(left.run(table), right.run(table), type);

            // negacion logica
            case NOT:
                return maker.not(left.run(table));

            // funcion null !!
            case NULL:
                return maker.isNull(left.run(table));
        }

        return null;
    }
}
