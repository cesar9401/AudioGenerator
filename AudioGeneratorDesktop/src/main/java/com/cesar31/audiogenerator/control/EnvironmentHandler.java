package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.SymbolTable;
import com.cesar31.audiogenerator.instruction.Var;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class EnvironmentHandler {

    private CastHandler cast;

    public EnvironmentHandler() {
        this.cast = new CastHandler();
    }

    public void addSymbolTable(Token type, Token id, Variable value, SymbolTable e, boolean keep, boolean assignment) {
        if (type != null) {
            Var kind = cast.getType(type);
            if (value != null) {
                Variable newType = cast.typeConversion(kind, value);
                if (newType != null) {
                    // En este punto el tipo de variable declarado es igual que el tipo de variable a asignar

                    // Agregar id y keep
                    newType.setId(id.getValue());
                    newType.setKeep(keep);
                    if (!e.containsVariable(newType.getId())) {
                        /* Agregar variable a table de simbolos */
                        e.add(newType);
                        //e.forEach(System.out::println);
                    } else {
                        System.out.println("Error, ya existe variable " + newType.getId());
                    }
                } else {
                    // Error, no es posible asignar
                    System.out.println("No es posible asignacion: " + kind + " = " + value.getType());
                }
            } else if (!assignment) {
                // Solo declaracion (no se asigno valor)
                Variable v = new Variable(kind, id.getValue(), null);
                v.setKeep(keep);
                if (!e.containsVariable(v.getId())) {
                    // Agregar a tabla
                    e.add(v);
                } else {
                    System.out.println("Error ya existe variable en declaracion");
                }
            }

        } else {
            System.out.println("No se indico tipo");
        }
    }

    public void makeAssignment(Token id, Variable a, SymbolTable e) {
        Variable v = this.getFromSymbolTable(id, e, false);
        if (v != null) {
            if (a != null) {
                if (a.getValue() != null) {
                    Variable newType = cast.typeConversion(v.getType(), a);
                    if (newType != null) {
                        v.setValue(newType.getValue());
                    } else {
                        System.out.println("No es posible asignar " + v.getId() + " = " + a.getType());
                    }
                }
            }
        } else {
            // Error se verifica en getFromSymbolTable
        }
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
                System.out.println("La variable " + id.getValue() + ", no existe. No es posible realizar asignacion");
                return null;
            }

            return v;
        }

        System.out.println("No existe variable " + id.getValue());
        return null;
    }
}
