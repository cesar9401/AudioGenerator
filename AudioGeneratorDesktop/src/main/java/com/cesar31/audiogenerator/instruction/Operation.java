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

    private ArrayAccess arrayItem;

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

    // Para acceso arreglos
    public Operation(OperationType type, ArrayAccess arrayItem) {
        this.type = type;
        this.arrayItem = arrayItem;
    }

    @Override
    public void sayName() {
        System.out.println("operation");
    }

    @Override
    public Variable run(SymbolTable table) {
        OperationMaker maker = new OperationMaker();
        switch (type) {
            case ARRAY_ACCESS:
                Variable v = this.arrayItem.run(table);
                return v;
            case ID:
                //System.out.println(value.getToken());
                Variable variable = table.getVariable(value.getToken().getValue());
                // System.out.println(variable);
                if (variable == null) {
                    // Variable no existe
                    System.out.println("Variable no existe -> " + value.getToken().getValue());
                } else if (variable.getValue() == null) {
                    // variable sin valor definido
                    System.out.println("Variable no tiene valor definido");
                    return null;
                }
                return variable;
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

    @Override
    public Integer getTab() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTab(Integer tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Variable getValue() {
        return value;
    }
}
