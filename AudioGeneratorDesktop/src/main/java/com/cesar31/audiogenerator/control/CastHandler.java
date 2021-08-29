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

    OperationHandler handler;

    public CastHandler(OperationHandler handler) {
        this.handler = handler;
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
            if (handler.isTest()) {
                return new Variable(type, "");
            }

            return new Variable(type, value.getValue());
            //return value;
        }

        // Asignacion a tipo entero
        if (type == INTEGER) {
            switch (value.getType()) {
                case DOUBLE:
                    if (handler.isTest()) {
                        return new Variable(INTEGER, "");
                    }

                    String val = String.valueOf(getDouble(value).intValue());
                    return new Variable(INTEGER, val);
                case CHAR:
                    if (handler.isTest()) {
                        return new Variable(INTEGER, "");
                    }

                    return new Variable(INTEGER, getAsciiCode(value).toString());
                case BOOLEAN:
                    if (handler.isTest()) {
                        return new Variable(INTEGER, "");
                    }

                    return new Variable(INTEGER, booleanToLong(value).toString());
                default:
                    return null;
            }
        }

        // Asignacion a tipo double
        if (type == DOUBLE) {
            switch (value.getType()) {
                case INTEGER:
                    if (handler.isTest()) {
                        return new Variable(DOUBLE, "");
                    }

                    return new Variable(DOUBLE, getDouble(value).toString());
                case CHAR:
                    if (handler.isTest()) {
                        return new Variable(DOUBLE, "");
                    }

                    return new Variable(DOUBLE, getAsciiCode(value).toString());
                default:
                    return null;
            }
        }

        // asignacion a tipo string
        if (type == STRING) {
            if (handler.isTest()) {
                return new Variable(STRING, "");
            }

            return new Variable(STRING, value.getValue());
        }

        if (type == CHAR) {
            switch (value.getType()) {
                case INTEGER:
                    if (handler.isTest()) {
                        return new Variable(CHAR, "");
                    }

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
        String value = type.getValue().toLowerCase();
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
