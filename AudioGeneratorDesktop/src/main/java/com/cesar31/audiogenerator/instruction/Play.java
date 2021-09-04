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
public class Play extends FunctionCall implements Serializable {

    private Token note;

    public Play(Token id, Token note, List<Operation> operations) {
        super(id, operations);
        this.note = note;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();
        List<Err> tmpE = new ArrayList<>();
        boolean error = false;

        String j = id.getValue() + "(";
        String h = j + note.getValue() + ",";
        for (int i = 0; i < operations.size(); i++) {
            Variable v = operations.get(i).run(table, handler);
            if (v != null) {
                j += v.getType().getName();
                h += v.getType().getName();

                if (v.getArray() != null) {
                    j += "[]";
                    h += "[]";
                }

                if (i != operations.size() - 1) {
                    j += ",";
                    h += ",";
                }

                values.add(v);

                if (v.getValue() == null & v.getArray() == null) {
                    error = true;

                    // Escribir mensaje de error aqui
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "En la llamada a la funcion `" + id.getValue() + "` se esta asignando una variable con valor nulo";
                    if (v.getId() != null) {
                        err.setLexema(v.getId());
                        description += ", variable con id `" + v.getId() + "`.";
                    }
                    err.setDescription(description);
                    tmpE.add(err);
                }
            }
        }

        j += ")";
        h += ")";
        j = j.toLowerCase();

        if (j.equals("reproducir(entero,entero,entero)")) {
            // Write your code here
            return handler.getRender().createSound(id, note, values.get(0), values.get(1), values.get(2));
        } else {
            if (error) {
                handler.getErrors().addAll(tmpE);
                return null;
            }
            Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
            String description = "La funcion con firma `" + h + "` no existe.";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();
        List<Err> tmpE = new ArrayList<>();
        boolean error = false;

        String j = id.getValue() + "(";
        String h = j + note.getValue() + ",";
        for (int i = 0; i < operations.size(); i++) {
            Variable v = operations.get(i).run(table, handler);
            if (v != null) {
                j += v.getType().getName();
                h += v.getType().getName();

                if (v.getArray() != null) {
                    j += "[]";
                    h += "[]";
                }

                if (i != operations.size() - 1) {
                    j += ",";
                    h += ",";
                }

                values.add(v);

                if (v.getValue() == null & v.getArray() == null) {
                    error = true;

                    // Escribir mensaje de error aqui
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "En la llamada a la funcion `" + id.getValue() + "` se esta asignando una variable con valor nulo";
                    if (v.getId() != null) {
                        err.setLexema(v.getId());
                        description += ", variable con id `" + v.getId() + "`.";
                    }
                    err.setDescription(description);
                    tmpE.add(err);
                }
            }
        }

        j += ")";
        h += ")";
        j = j.toLowerCase();

        if (j.equals("reproducir(entero,entero,entero)")) {
            // Write your code here
            return new Variable(Var.INTEGER, values.get(1).getValue());
        } else {
            if (error) {
                handler.getErrors().addAll(tmpE);
                return null;
            }
            Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
            String description = "La funcion con firma `" + h + "` no existe.";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }

    public Token getNote() {
        return note;
    }
}
