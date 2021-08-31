package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class FunctionCall implements Instruction {

    protected Token id;
    protected List<Operation> operations;

    public FunctionCall(Token id) {
        this.id = id;
    }

    public FunctionCall(Token id, List<Operation> operations) {
        this.id = id;
        this.operations = operations;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();
        boolean error = false;
        List<Err> tmpE = new ArrayList<>();

        String functionId = id.getValue() + "(";
        for (int i = 0; i < operations.size(); i++) {
            Variable v = operations.get(i).run(table, handler);
            // System.out.println(v);
            if (v != null) {
                functionId += v.getType().getName();

                if (v.getArray() != null) {
                    functionId += "[]";
                }

                if (i != operations.size() - 1) {
                    functionId += ",";
                }
                values.add(v);

                // Si no tienen valor definido
                if (v.getValue() == null && v.getArray() == null) {
                    error = true;
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "En la llamada a la funcion `" + id.getValue() + "` se esta asignando una variable con valor nulo";
                    if (v.getId() != null) {
                        err.setLexema(v.getId());
                        description += ", variable con id `" + v.getId() + "`.";
                        if (v.getToken() != null) {
                            Token t = v.getToken();
                            err.setLine(t.getLine());
                            err.setColumn(t.getColumn());
                        }
                    }
                    err.setDescription(description);
                    tmpE.add(err);
                }
            }
        }
        functionId += ")";

        Function f = handler.getFunctions().get(functionId);
        if (f != null) {
            if (!error) {
                f.setValues(values);
                Object o = f.run(table, handler);
                if (o != null) {
                    return o;
                } else {
                    return f;
                }
            } else {
                handler.getErrors().addAll(tmpE);
            }
        } else {
            String i = functionId.toLowerCase();
            if (i.equals("longitud(cadena)") || i.equals("longitud(cadena[])") || i.equals("longitud(entero[])") || i.equals("longitud(doble[])") || i.equals("longitud(caracter[])") || i.equals("longitud(boolean[])")) {
                if (!error) {
                    Length len = new Length(this.id);
                    len.setValues(values);
                    return len.run(table, handler);
                } else {
                    handler.getErrors().addAll(tmpE);
                    return null;
                }
            } else if (i.equals("sumarizar(cadena[])") || i.equals("sumarizar(entero[])") || i.equals("sumarizar(doble[])") || i.equals("sumarizar(caracter[])") || i.equals("sumarizar(boolean[])")) {
                if (!error) {
                    Summarize sum = new Summarize(this.id);
                    sum.setValues(values);
                    return sum.run(table, handler);
                } else {
                    handler.getErrors().addAll(tmpE);
                    return null;
                }
            } else if (i.equals("esperar(entero,entero)")) {
                if (!error) {
                    Wait wait = new Wait(this.id);
                    wait.setValues(values);
                    return wait.run(table, handler);
                } else {
                    handler.getErrors().addAll(tmpE);
                    return null;
                }
            }

            Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
            String description = "La funcion con firma `" + functionId + "` no existe.";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }

    /**
     * Se ejecuta en llamadas a funciones void
     *
     * @param table
     * @param handler
     * @return
     */
    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        List<Variable> values = new ArrayList<>();
        boolean error = false;
        List<Err> tmpE = new ArrayList<>();

        String functionId = id.getValue() + "(";
        for (int i = 0; i < operations.size(); i++) {
            Variable v = operations.get(i).test(table, handler);
            if (v != null) {
                functionId += v.getType().getName();

                if (v.getArray() != null) {
                    functionId += "[]";
                }

                if (i != operations.size() - 1) {
                    functionId += ",";
                }
                values.add(v);

                // Si no tienen valor definido
                if (v.getValue() == null && v.getArray() == null) {
                    error = true;
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "En la llamada a la funcion `" + id.getValue() + "` se esta asignando una variable con valor nulo";
                    if (v.getId() != null) {
                        err.setLexema(v.getId());
                        description += ", variable con id `" + v.getId() + "`.";
                        if (v.getToken() != null) {
                            Token t = v.getToken();
                            err.setLine(t.getLine());
                            err.setColumn(t.getColumn());
                        }
                    }
                    err.setDescription(description);
                    tmpE.add(err);
                }
            }
        }
        functionId += ")";

        Function f = handler.getFunctions().get(functionId);
        if (f != null) {
            if (!error) {
                f.setValues(values);
                // f.test(table, handler);
            } else {
                handler.getErrors().addAll(tmpE);
            }
        } else {
            String i = functionId.toLowerCase();
            if (i.equals("longitud(cadena)") || i.equals("longitud(cadena[])") || i.equals("longitud(entero[])") || i.equals("longitud(doble[])") || i.equals("longitud(caracter[])") || i.equals("longitud(boolean[])")) {
                if (!error) {
                    Length len = new Length(this.id);
                    len.setValues(values);
                    return len.test(table, handler);
                } else {
                    handler.getErrors().addAll(tmpE);
                    return null;
                }
            } else if (i.equals("sumarizar(cadena[])") || i.equals("sumarizar(entero[])") || i.equals("sumarizar(doble[])") || i.equals("sumarizar(caracter[])") || i.equals("sumarizar(boolean[])")) {
                if (!error) {
                    Summarize sum = new Summarize(this.id);
                    sum.setValues(values);
                    return sum.test(table, handler);
                } else {
                    handler.getErrors().addAll(tmpE);
                    return null;
                }
            } else if (i.equals("esperar(entero,entero)")) {
                if (!error) {
                    Wait wait = new Wait(this.id);
                    wait.setValues(values);
                    return wait.run(table, handler);
                } else {
                    handler.getErrors().addAll(tmpE);
                    return null;
                }
            }

            Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
            String description = "La funcion con firma `" + functionId + "` no existe.";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }

    @Override
    public Token getInfo() {
        return this.id;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
