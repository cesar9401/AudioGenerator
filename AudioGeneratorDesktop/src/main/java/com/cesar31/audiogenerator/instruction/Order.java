package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Order extends FunctionCall implements Serializable {

    private Token way;

    public Order(Token id, List<Operation> operations, Token way) {
        super(id, operations);
        this.way = way;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();
        boolean error = false;
        List<Err> tmpE = new ArrayList<>();

        String j = super.getInfo().getValue() + "(";
        for (int i = 0; i < super.getOperations().size(); i++) {
            Variable v = super.getOperations().get(i).run(table, handler);
            if (v != null) {
                j += v.getType().getName();

                if (v.getArray() != null) {
                    j += "[]";
                }

                if (i != super.getOperations().size() - 1) {
                    j += ",";
                }

                values.add(v);

                if (v.getValue() == null && v.getArray() == null) {
                    error = true;
                    // Escribir mensaje de error aqui
                    Err err = new Err(Err.TypeErr.SINTACTICO, super.getInfo().getLine(), super.getInfo().getColumn(), super.getInfo().getValue());
                    String description = "En la llamada a la funcion `" + super.getInfo().getValue() + "` se esta asignando una variable con valor nulo";
                    if (v.getId() != null) {
                        err.setLexema(v.getId());
                        description += ", variable con id `" + v.getId() + "`.";
                    }
                    err.setDescription(description);
                    tmpE.add(err);
                }
            }
        }

        String h = j + "," + way.getValue() + ")";
        j += ")";
        j = j.toLowerCase();

        if (j.equals("ordenar(cadena[])") || j.equals("ordenar(entero[])") || j.equals("ordenar(doble[])") || j.equals("ordenar(caracter[])") || j.equals("ordenar(boolean[])")) {
            Operation tmp = super.getOperations().get(0);
            return handler.getNativeF().order(tmp, way, super.getInfo(), table);
        } else {
            if (error) {
                handler.getErrors().addAll(tmpE);
                return null;
            }
            Err err = new Err(Err.TypeErr.SINTACTICO, super.getInfo().getLine(), super.getInfo().getColumn(), super.getInfo().getValue());
            String description = "La funcion con firma `" + h + "` no existe.";
            err.setDescription(description);
            handler.getErrors().add(err);
        }
        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();
        boolean error = false;
        List<Err> tmpE = new ArrayList<>();

        String j = super.getInfo().getValue() + "(";
        for (int i = 0; i < super.getOperations().size(); i++) {
            Variable v = super.getOperations().get(i).test(table, handler);
            if (v != null) {
                j += v.getType().getName();

                if (v.getArray() != null) {
                    j += "[]";
                }

                if (i != super.getOperations().size() - 1) {
                    j += ",";
                }

                values.add(v);

                if (v.getValue() == null && v.getArray() == null) {
                    error = true;
                    // Escribir mensaje de error aqui
                    Err err = new Err(Err.TypeErr.SINTACTICO, super.getInfo().getLine(), super.getInfo().getColumn(), super.getInfo().getValue());
                    String description = "En la llamada a la funcion `" + super.getInfo().getValue() + "` se esta asignando una variable con valor nulo";
                    if (v.getId() != null) {
                        err.setLexema(v.getId());
                        description += ", variable con id `" + v.getId() + "`.";
                    }
                    err.setDescription(description);
                    tmpE.add(err);
                }
            }
        }

        String h = j + "," + way.getValue() + ")";
        j += ")";
        j = j.toLowerCase();

        if (j.equals("ordenar(cadena[])") || j.equals("ordenar(entero[])") || j.equals("ordenar(doble[])") || j.equals("ordenar(caracter[])") || j.equals("ordenar(boolean[])")) {
            Operation tmp = super.getOperations().get(0);
            //return handler.getNativeF().order(tmp, way, super.getInfo(), table);
            return new Variable(Var.INTEGER, "0");
        } else {
            if (error) {
                handler.getErrors().addAll(tmpE);
                return null;
            }
            Err err = new Err(Err.TypeErr.SINTACTICO, super.getInfo().getLine(), super.getInfo().getColumn(), super.getInfo().getValue());
            String description = "La funcion con firma `" + h + "` no existe.";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }

    public Token getWay() {
        return way;
    }
}
