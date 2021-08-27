package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.Assignment;
import com.cesar31.audiogenerator.instruction.Operation;
import com.cesar31.audiogenerator.instruction.SymbolTable;
import com.cesar31.audiogenerator.instruction.Var;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class EnvironmentHandler {

    private OperationHandler handler;

    public EnvironmentHandler(OperationHandler handler) {
        this.handler = handler;
    }

    /**
     * Agregar declaracion / declaracion y asignacion a tabla de simbolos
     *
     * @param type
     * @param id
     * @param value
     * @param e
     * @param keep
     * @param assignment
     */
    public void addSymbolTable(Token type, Token id, Variable value, SymbolTable e, boolean keep, boolean assignment) {
        if (type != null) {
            Var kind = this.handler.getCast().getType(type);
            if (value != null) {
                if (value.getValue() != null) {
                    
                    Variable newType = this.handler.getCast().typeConversion(kind, value);
                    if (newType != null) {
                        // En este punto el tipo de variable declarado es igual que el tipo de variable a asignar

                        // Agregar id y keep
                        newType.setId(id.getValue());
                        newType.setKeep(keep);
                        if (!e.containsVariable(newType.getId())) {
                            /* Agregar variable a table de simbolos */
                            e.add(newType);
                        } else {
                            // Error la variable ya existe
                            Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                            String description = "La variable `" + id.getValue() + "` ya esta definida en este ambito, intente con otro identificador.";
                            err.setDescription(description);
                            this.handler.getErrors().add(err);
                        }
                    } else {
                        // Error, no es posible asignar por los tipos
                        Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                        String description = "No es posible realizar una asignacion entre la variable `" + id.getValue() + "` de tipo `" + kind.getName() + "`";
                        description += " y el valor a asignar de tipo `" + value.getType().getName() + "`.";
                        err.setDescription(description);
                        this.handler.getErrors().add(err);
                    }
                } else {
                    // System.out.println("Error here, value.getValue() is null");
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "Se esta intentando realizar una asignacion a la variable `" + id.getValue() + "` de un valor null. ";
                    description += value.getId() != null ? "Esto se debe a que la variable `" + value.getId() + "`, no tiene un valor definido." : "Esto se debe a que probablemente uno de los operadores a asignar no tiene un valor definido.";
                    err.setDescription(description);
                    this.handler.getErrors().add(err);
                }
            } else if (!assignment) {
                // Solo declaracion (no se asigno valor)
                Variable v = new Variable(kind, id.getValue(), null);
                v.setKeep(keep);
                if (!e.containsVariable(v.getId())) {
                    // Agregar a tabla
                    e.add(v);
                } else {
                    // Error la variable ya existe
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "La variable `" + id.getValue() + "` ya esta definida en este ambito, intente con otro identificador.";
                    err.setDescription(description);
                    this.handler.getErrors().add(err);
                }
            } else {
                // Error, se esta intendo asignar un valor nulo
                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                String description = "Se esta intentando realizar una asignacion a la variable `" + id.getValue() + "` de un valor null. ";
                description += "Esto se debe a que probablemente uno de los operadores a asignar no tiene un valor definido.";
                err.setDescription(description);
                this.handler.getErrors().add(err);
            }
        }
    }

    /**
     * Realizar asignacion
     *
     * @param kindA
     * @param id
     * @param value
     * @param e
     */
    public void makeAssignment(Assignment.TypeA kindA, Token id, Operation value, SymbolTable e) {
        Variable v = this.getFromSymbolTable(id, e, false);
        if (v != null) {
            if (v.getArray() != null) {
                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                String description = "La variable `" + id.getValue() + "` corresponde a una variable de tipo `arreglo de " + v.getType().getName() + "`, para acceder a los elementos de un arreglo, debe de indicar los indices del mismo.";
                err.setDescription(description);
                this.handler.getErrors().add(err);
                return;
            }

            switch (kindA) {
                case EQUAL:
                    // do nothing
                    break;
                case MINUS_MINUS:
                    if (v.getType() == Var.BOOLEAN || v.getType() == Var.STRING) {
                        errorAssignment(id, value, v.getType());
                        return;
                    }

                    if (v.getValue() == null) {
                        errorAssignmentWithNoValue(id, value);
                        return;
                    }
                    break;
                case PLUS_PLUS:
                    if (v.getType() == Var.BOOLEAN) {
                        errorAssignment(id, value, v.getType());
                        return;
                    }

                    if (v.getValue() == null) {
                        errorAssignmentWithNoValue(id, value);
                        return;
                    }
                    break;
                case PLUS_EQ:
                    if (v.getType() == Var.BOOLEAN) {
                        errorAssignment(id, value, v.getType());
                        return;
                    }

                    if (v.getValue() == null) {
                        errorAssignmentWithNoValue(id, value);
                        return;
                    }
                    break;
            }

            Variable a = value.run(e, handler);
            if (a != null) {
                if (a.getValue() != null) {
                    Variable newType = this.handler.getCast().typeConversion(v.getType(), a);
                    if (newType != null) {
                        v.setValue(newType.getValue());
                    } else {
                        // Error, no es posible asignar por los tipos
                        Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                        String description = "No es posible realizar una asignacion entre la variable `" + id.getValue() + "` de tipo `" + v.getType().getName() + "`";
                        description += " y el valor a asignar `" + a.getValue() + "` de tipo `" + a.getType().getName() + "`.";
                        err.setDescription(description);
                        this.handler.getErrors().add(err);
                    }
                } else {
                    // Error, uno de los operadores puede tener un valor no definido
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "Se esta intentando realizar una asignacion a la variable `" + id.getValue() + "` de un valor null. ";
                    description += a.getId() != null ? "Esto se debe a que la variable `" + a.getId() + "`, no tiene un valor definido." : "Esto se debe a que probablemente uno de los operadores a asignar no tiene un valor definido.";
                    err.setDescription(description);
                    this.handler.getErrors().add(err);
                }
            } else {
                // Error, uno de los operadores puede tener un valor no definido
                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                String description = "Se esta intentando realizar una asignacion a la variable `" + id.getValue() + "` de un valor null. ";
                description += "Esto se debe a que probablemente uno de los operadores a asignar no tiene un valor definido.";
                err.setDescription(description);
                this.handler.getErrors().add(err);
            }
        } else {
            // Error se verifica en getFromSymbolTable
        }
    }

    private void errorAssignment(Token id, Operation value, Var type) {
        Token t = value.getOp();
        Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
        String description = "La asignacion definida por el operador `" + t.getValue() + "` no es aplicable a variables de tipo `" + type.getName() + "` como lo es la variable `" + id.getValue() + "`.";
        err.setDescription(description);
        this.handler.getErrors().add(err);
    }

    private void errorAssignmentWithNoValue(Token id, Operation value) {
        Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
        String description = "En la asignacion definida por el operador `" + value.getOp().getValue() + "`, la variable `" + id.getValue() + "` no tiene valor definido, por lo tanto no es posible proceder con la asignacion.";
        err.setDescription(description);
        this.handler.getErrors().add(err);
    }

    /**
     * Obtener variable de la tabla de simbolos
     *
     * @param id
     * @param e
     * @param assignment
     * @return
     */
    public Variable getFromSymbolTable(Token id, SymbolTable e, boolean assignment) {
        Variable v = e.getVariable(id.getValue());

        if (v != null) {
            if (v.getValue() == null && assignment) {
                // La variable no tiene valor definido
                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                String description = "La variable `" + id.getValue() + "` no tiene valor definido, no es posible realizar la asignacion.";
                err.setDescription(description);
                this.handler.getErrors().add(err);
                return null;
            }

            return v;
        }

        // La variable no existe
        Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
        String description = "No se han encontrado al simbolo `" + id.getValue() + "`, este no ha sido declarado.";
        err.setDescription(description);
        this.handler.getErrors().add(err);
        return null;
    }
}
