package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.Var;
import static com.cesar31.audiogenerator.instruction.Var.*;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class CastHandler {

    public CastHandler() {
    }

    /**
     * Metodo para convertir un tipo de variable a otro segun el tipo de
     * asignacion
     *
     * @param type el tipo de variable a la que sera asignada
     * @param value el tipo y valor de la variable a asignar
     * @return
     */
    public Variable typeConversion(Var type, Variable value) {
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

        if (type == BOOLEAN) {
            switch (value.getType()) {
                case INTEGER:
                    if (value.getValue().equals("1") || value.getValue().equals("0")) {
                        return new Variable(BOOLEAN, value.getValue());
                    }
                    return null;
            }
        }

        return null;
    }

    public Var getType(Token type) {
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
