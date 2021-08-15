package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.SymbolTable;
import com.cesar31.audiogenerator.instruction.Var;
import static com.cesar31.audiogenerator.instruction.Var.*;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class EnvironmentHandler {
    
    public void addSymbolTable(Token type, Token id, Variable value, SymbolTable e, boolean keep, boolean assignment) {
        if (type != null) {
            Var kind = getType(type);
            if (value != null) {
                Variable newType = typeConversion(kind, value);
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
                    Variable newType = this.typeConversion(v.getType(), a);
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

    /**
     * Metodo para convertir un tipo de variable a otro segun el tipo de
     * asignacion
     *
     * @param type el tipo de variable a la que sera asignada
     * @param value el tipo y valor de la variable a asignar
     * @return
     */
    private Variable typeConversion(Var type, Variable value) {
        // asignacion del mismo tipo
        if (value.getType() == type) {
            return new Variable(type, value.getValue());
            //return value;
        }

        // Asignacion a tipo entero
        if (type == INTEGER) {
            switch (value.getType()) {
                case DOUBLE:
                    String val = String.valueOf(getDouble(value).intValue());
                    return new Variable(INTEGER, val);
                case CHAR:
                    return new Variable(INTEGER, getAsciiCode(value).toString());
                case BOOLEAN:
                    return new Variable(INTEGER, booleanToLong(value).toString());
                default:
                    return null;
            }
        }

        // Asignacion a tipo double
        if (type == DOUBLE) {
            switch (value.getType()) {
                case INTEGER:
                    return new Variable(DOUBLE, getDouble(value).toString());
                case CHAR:
                    return new Variable(DOUBLE, getAsciiCode(value).toString());
                default:
                    return null;
            }
        }

        // asignacion a tipo string
        if (type == STRING) {
            return new Variable(STRING, value.getValue());
        }
        
        if (type == CHAR) {
            switch (value.getType()) {
                case INTEGER:
                    return new Variable(CHAR, integerToAsciiCode(value).toString());
                default:
                    return null;
            }
        }
        
        return null;
    }
    
    private Var getType(Token type) {
        String value = type.getValue();
        switch (value) {
            case "entero":
                return INTEGER;
            case "doble":
                return DOUBLE;
            case "caracter":
                return CHAR;
            case "boolean":
                return BOOLEAN;
            case "cadena":
                return STRING;
            default:
                return VOID;
        }
    }
    
    private Double getDouble(Variable v) {
        return Double.valueOf(v.getValue());
    }
    
    private Long getLong(Variable v) {
        return Long.valueOf(v.getValue());
    }
    
    private Boolean getBoolean(Variable v) {
        String value = v.getValue();
        return value.equalsIgnoreCase("verdadero") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("1");
    }
    
    private Long booleanToLong(Variable v) {
        Boolean value = getBoolean(v);
        return value ? Long.valueOf(1) : Long.valueOf(0);
    }
    
    private Long getAsciiCode(Variable v) {
        return (long) v.getValue().charAt(0);
    }
    
    private Character integerToAsciiCode(Variable v) {
        int value = getLong(v).intValue();
        return (char) value;
    }
    
    private Double formatDouble(Double value) {
        return Math.round(value * 100_000d) / 100_000d;
    }
}
