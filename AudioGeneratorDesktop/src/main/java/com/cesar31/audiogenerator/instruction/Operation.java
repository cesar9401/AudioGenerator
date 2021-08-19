package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.control.OperationMaker;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Operation implements Instruction {

    private Variable value;
    private OperationType type;
    private Operation left;
    private Operation right;

    // Token del operador
    private Token op;

    private ArrayAccess arrayItem;

    // Operaciones definidas con un valor o id
    public Operation(OperationType type, Variable value) {
        this.type = type;
        this.value = value;
    }

    // Operaciones unarias
    public Operation(OperationType type, Operation left, Token op) {
        this.type = type;
        this.left = left;
        this.op = op;
    }

    // Operaciones con dos operadores
    public Operation(OperationType type, Operation left, Operation right, Token op) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.op = op;
    }

    // Para acceso arreglos
    public Operation(OperationType type, ArrayAccess arrayItem) {
        this.type = type;
        this.arrayItem = arrayItem;
    }

    @Override
    public Variable run(SymbolTable table, OperationHandler handler) {
        OperationMaker maker = handler.getOperation();
        switch (type) {
            case ARRAY_ACCESS:
                Variable v = this.arrayItem.run(table, handler);
                return v;
            case ID:
                Variable variable = table.getVariable(value.getToken().getValue());
                if (variable == null) {
                    Token t = value.getToken();
                    Err e = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                    String description = "La variable " + t.getValue() + ", no ha sido declarada.";
                    e.setDescription(description);
                    handler.getErrors().add(e);
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
                return maker.sum(left.run(table, handler), right.run(table, handler), this.op);
            case SUBTRACTION:
                return maker.subtraction(left.run(table, handler), right.run(table, handler), this.op);
            case MULTIPLICATION:
                return maker.multiplication(left.run(table, handler), right.run(table, handler), this.op);
            case DIVISION:
                return maker.division(left.run(table, handler), right.run(table, handler), this.op);
            case MOD:
                return maker.mod(left.run(table, handler), right.run(table, handler), this.op);
            case POW:
                return maker.pow(left.run(table, handler), right.run(table, handler), this.op);
            case UMINUS:
                return maker.uminus(left.run(table, handler), this.op);

            // operaciones relacionales
            case EQEQ:
            case GREATER:
            case GREATER_OR_EQUAL:
            case LESS_OR_EQUAL:
            case SMALLER:
            case NEQ:
                return maker.compare(left.run(table, handler), right.run(table, handler), type, this.op);

            // operaciones logicas
            case AND:
            case NAND:
            case OR:
            case NOR:
            case XOR:
                return maker.logical(left.run(table, handler), right.run(table, handler), type, this.op);

            // negacion logica
            case NOT:
                return maker.not(left.run(table, handler), this.op);

            // funcion null !!
            case NULL:
                return maker.isNull(left.run(table, handler));
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
