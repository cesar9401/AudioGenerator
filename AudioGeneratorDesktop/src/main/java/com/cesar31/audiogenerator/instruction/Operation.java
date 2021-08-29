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

    // Acceso a arreglo
    private ArrayAccess arrayItem;

    // llamada de funcion
    private FunctionCall call;

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

    // para llamada de funciones
    public Operation(OperationType type, FunctionCall call) {
        this.type = type;
        this.call = call;
    }

    @Override
    public Variable run(SymbolTable table, OperationHandler handler) {
        OperationMaker maker = handler.getOperation();
        switch (type) {
            case FUNCTION_CALL:
                Variable val1 =  (Variable) call.run(table, handler);
                //System.out.println(val1);
                return val1;
            case ARRAY_ACCESS:
                Variable v = this.arrayItem.run(table, handler);
                return v;
            case ID:
                Variable variable = table.getVariable(value.getToken().getValue());
                if (variable == null) {
                    Token t = value.getToken();
                    Err e = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                    String description = "No se han encontrado al simbolo `" + t.getValue() + "` este no ha sido declarado.";
                    e.setDescription(description);
                    handler.getErrors().add(e);
                } else if (variable.getArray() != null) {
                    // Se maneja error al verificar que v.getValue() is null
                    // System.out.println("Variable de tipo arreglo!!!");
                }
                return variable;
            case INTEGER:
            case STRING:
            case BOOLEAN:
                return value;
            case CHAR: // revisar longitud
                if (value.getValue().length() != 1) {
                    if (value.getToken() != null) {
                        Token tmp = value.getToken();
                        Err e = new Err(Err.TypeErr.SEMANTICO, tmp.getLine(), tmp.getColumn(), tmp.getValue());
                        String description = "Las variables de tipo `caracter` deben de tener longitud de 1, el valor = `" + tmp.getValue() + "`, no cumple con la longitud esperada.";
                        e.setDescription(description);
                        handler.getErrors().add(e);
                    }
                }
                return value;

            case DOUBLE: // revisar numero de cifras decimales
                String val = value.getValue();
                int index = val.indexOf(".");
                int count = val.substring(index + 1).length();

                if (count > 6) {
                    if (value.getToken() != null) {
                        Token tmp = value.getToken();
                        Err e = new Err(Err.TypeErr.SEMANTICO, tmp.getLine(), tmp.getColumn(), tmp.getValue());
                        String description = "Para las variables de tipo `doble`, el maximo de cifras decimales permitidas es 6. El valor `" + tmp.getValue() + "` no cumple con la longitud maxima permitida.";
                        e.setDescription(description);
                        handler.getErrors().add(e);
                    }
                }
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
    public Variable test(SymbolTable table, OperationHandler handler) {
        return this.run(table, handler);
    }

    public Token getOp() {
        return op;
    }

    @Override
    public Token getInfo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
