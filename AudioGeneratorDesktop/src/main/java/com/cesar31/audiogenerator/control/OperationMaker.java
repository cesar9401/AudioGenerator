package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.OperationType;
import com.cesar31.audiogenerator.instruction.Var;
import com.cesar31.audiogenerator.instruction.Variable;
import static com.cesar31.audiogenerator.instruction.Var.*;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class OperationMaker {

    private OperationHandler handler;

    public OperationMaker(OperationHandler handler) {
        this.handler = handler;
    }

    private void errorForOperations(Variable a, Token op, String name) {
        if (a.getValue() == null && a.getArray() == null) {
            Err e = new Err(Err.TypeErr.SINTACTICO, op.getLine(), op.getColumn(), op.getValue());
            String description = "En la operacion definida por el operador `" + op.getValue() + "` (" + name + ")";
            description += a.getId() != null ? ", la variable `" + a.getId() + "` no tiene un valor definido. " : ", uno de los operadores no tiene un valor definido. ";
            description += "No es posible procesar la operacion.";
            e.setDescription(description);
            this.handler.getErrors().add(e);
        } else if (a.getArray() != null) {
            Err e = new Err(Err.TypeErr.SINTACTICO, op.getLine(), op.getColumn(), op.getValue());
            String description = "En la operacion definida por el operador `" + op.getValue() + "` (" + name + ")";
            description += ", la variable `" + a.getId() + "` es de tipo `arreglo de " + a.getType().getName() + "`, por lo tanto la operacion no puede ser procesada.";
            e.setDescription(description);
            this.handler.getErrors().add(e);
        }
    }

    private void errorOfTypes(Variable a, Variable b, Token op, String name) {
        Err e = new Err(Err.TypeErr.SINTACTICO, op.getLine(), op.getColumn(), op.getValue());
        String description = "No es posible realizar la operacion definida por el operador `" + op.getValue() + "` (" + name + ")";
        description += " entre variables de tipos: `" + a.getType().getName() + "` y `" + b.getType().getName() + "`.";
        e.setDescription(description);
        this.handler.getErrors().add(e);
    }

    private void errorOfTypes(Variable a, Token op, String name) {
        Err e = new Err(Err.TypeErr.SINTACTICO, op.getLine(), op.getColumn(), op.getValue());
        String description = "No es posible realizar la operacion definida por el operador `" + op.getValue() + "` (" + name + ")";
        description += " para la variable de tipo: `" + a.getType().getName() + "`.";
        e.setDescription(description);
        this.handler.getErrors().add(e);
    }

    public Variable sum(Variable a, Variable b, Token op) {
        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, "suma");
            errorForOperations(b, op, "suma");
            return null;
        }

        // double + double
        if (checkTypes(a, DOUBLE, b, DOUBLE)) {
            Double value = getDouble(a) + getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double + integer OR integer + double
        if (a.getType() == DOUBLE && b.getType() == INTEGER || a.getType() == INTEGER && b.getType() == DOUBLE) {
            Double value = getDouble(a) + getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double + char
        if (checkTypes(a, DOUBLE, b, CHAR)) {
            Double value = getDouble(a) + getAsciiCode(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // char + double
        if (checkTypes(a, CHAR, b, DOUBLE)) {
            Double value = getAsciiCode(a) + getDouble(a);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double + boolean
        if (checkTypes(a, DOUBLE, b, BOOLEAN)) {
            Double value = getDouble(a) + booleanToLong(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // boolaen + double
        if (checkTypes(a, BOOLEAN, b, DOUBLE)) {
            Double value = booleanToLong(a) + getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // integer + integer
        if (checkTypes(a, INTEGER, b, INTEGER)) {
            Long value = getLong(a) + getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // entero + char
        if (checkTypes(a, INTEGER, b, CHAR)) {
            Long value = getLong(a) + getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // char + entero
        if (checkTypes(a, CHAR, b, INTEGER)) {
            Long value = getAsciiCode(a) + getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // entero + boolean
        if (checkTypes(a, INTEGER, b, BOOLEAN)) {
            Long value = getLong(a) + booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean + entero
        if (checkTypes(a, BOOLEAN, b, INTEGER)) {
            Long value = booleanToLong(a) + getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // cadena + cadena
        if (checkTypes(a, STRING, b, STRING)) {
            String value = a.getValue().concat(b.getValue());
            return new Variable(STRING, value);
        }

        // cadena + lo que sea
        if (a.getType() == STRING || b.getType() == STRING) {
            String value = a.getValue().concat(b.getValue());
            return new Variable(STRING, value);
        }

        // boolean + boolean
        if (checkTypes(a, BOOLEAN, b, BOOLEAN)) {
            Long value = booleanToLong(a) + booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter + caracter
        if (checkTypes(a, CHAR, b, CHAR)) {
            Long value = getAsciiCode(a) + getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter + boolean
        if (checkTypes(a, CHAR, b, BOOLEAN)) {
            Long value = getAsciiCode(a) + booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean + caracter
        if (checkTypes(a, BOOLEAN, b, CHAR)) {
            Long value = booleanToLong(a) + getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        errorOfTypes(a, b, op, "suma");
        return null;
    }

    public Variable subtraction(Variable a, Variable b, Token op) {

        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, "resta");
            errorForOperations(b, op, "resta");
            return null;
        }

        // double - double/integer
        if (a.getType() == DOUBLE && (b.getType() == DOUBLE || b.getType() == INTEGER)) {
            Double value = getDouble(a) - getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // integer - double
        if (checkTypes(a, INTEGER, b, DOUBLE)) {
            Double value = getDouble(a) - getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double - char
        if (checkTypes(a, DOUBLE, b, CHAR)) {
            Double value = getDouble(a) - getAsciiCode(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // char - double
        if (checkTypes(a, CHAR, b, DOUBLE)) {
            Double value = getAsciiCode(a) - getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double - boolean
        if (checkTypes(a, DOUBLE, b, BOOLEAN)) {
            Double value = getDouble(a) - booleanToLong(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // boolean - double
        if (checkTypes(a, BOOLEAN, b, DOUBLE)) {
            Double value = booleanToLong(a) - getDouble(a);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // integer - char
        if (checkTypes(a, INTEGER, b, CHAR)) {
            Long value = getLong(a) - getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // char - integer
        if (checkTypes(a, CHAR, b, INTEGER)) {
            Long value = getAsciiCode(a) - getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // integer - boolean
        if (checkTypes(a, INTEGER, b, BOOLEAN)) {
            Long value = getLong(a) - booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean - integer
        if (checkTypes(a, BOOLEAN, b, INTEGER)) {
            Long value = booleanToLong(a) - getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // integer - integer
        if (checkTypes(a, INTEGER, b, INTEGER)) {
            Long value = getLong(a) - getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean - boolean
        if (checkTypes(a, BOOLEAN, b, BOOLEAN)) {
            Long value = booleanToLong(a) - booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter - caracter
        if (checkTypes(a, CHAR, b, CHAR)) {
            Long value = getAsciiCode(a) - getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter - boolean
        if (checkTypes(a, CHAR, b, BOOLEAN)) {
            Long value = getAsciiCode(a) - booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean - caracter
        if (checkTypes(a, BOOLEAN, b, CHAR)) {
            Long value = booleanToLong(a) - getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        errorOfTypes(a, b, op, "resta");
        return null;
    }

    public Variable multiplication(Variable a, Variable b, Token op) {
        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, "multiplicacion");
            errorForOperations(b, op, "multiplicacion");
            return null;
        }

        // double * double
        if (checkTypes(a, DOUBLE, b, DOUBLE)) {
            Double value = getDouble(a) * getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double * integer / integer * double
        if (checkTypes(a, DOUBLE, b, INTEGER) || checkTypes(a, INTEGER, b, DOUBLE)) {
            Double value = getDouble(a) * getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double * char
        if (checkTypes(a, DOUBLE, b, CHAR)) {
            Double value = getDouble(a) * getAsciiCode(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // char * double
        if (checkTypes(a, CHAR, b, DOUBLE)) {
            Double value = getAsciiCode(a) * getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double * boolean
        if (checkTypes(a, DOUBLE, b, BOOLEAN)) {
            Double value = getDouble(a) * booleanToLong(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // boolaen * double
        if (checkTypes(a, BOOLEAN, b, DOUBLE)) {
            Double value = booleanToLong(a) * getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // integer * integer
        if (checkTypes(a, INTEGER, b, INTEGER)) {
            Long value = getLong(a) * getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // integer * char
        if (checkTypes(a, INTEGER, b, CHAR)) {
            Long value = getLong(a) * getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // char * integer
        if (checkTypes(a, CHAR, b, INTEGER)) {
            Long value = getAsciiCode(a) * getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // integer * boolean
        if (checkTypes(a, INTEGER, b, BOOLEAN)) {
            Long value = getLong(a) * booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean * integer
        if (checkTypes(a, BOOLEAN, b, INTEGER)) {
            Long value = booleanToLong(a) * getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean * boolean
        if (checkTypes(a, BOOLEAN, b, BOOLEAN)) {
            Long value = booleanToLong(a) * booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter * caracter
        if (checkTypes(a, CHAR, b, CHAR)) {
            Long value = getAsciiCode(a) * getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter * boolean
        if (checkTypes(a, CHAR, b, BOOLEAN)) {
            Long value = getAsciiCode(a) * booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean * caracter
        if (checkTypes(a, BOOLEAN, b, CHAR)) {
            Long value = booleanToLong(a) * getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        errorOfTypes(a, b, op, "multiplicacion");
        return null;
    }

    public Variable division(Variable a, Variable b, Token op) {
        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, "division");
            errorForOperations(b, op, "division");
            return null;
        }

        // double / double
        if (checkTypes(a, DOUBLE, b, DOUBLE)) {
            Double value = getDouble(a) / getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double / integer OR integer * double
        if (checkTypes(a, DOUBLE, b, INTEGER) || checkTypes(a, INTEGER, b, DOUBLE)) {
            Double value = getDouble(a) / getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double * char
        if (checkTypes(a, DOUBLE, b, CHAR)) {
            Double value = getDouble(a) / getAsciiCode(b).doubleValue();
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // char * double
        if (checkTypes(a, CHAR, b, DOUBLE)) {
            Double value = getAsciiCode(a).doubleValue() / getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // double / boolean
        if (checkTypes(a, DOUBLE, b, BOOLEAN)) {
            Double value = getDouble(a) / booleanToLong(b).doubleValue();
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // boolaen / double
        if (checkTypes(a, BOOLEAN, b, DOUBLE)) {
            Double value = booleanToLong(a).doubleValue() / getDouble(b);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        // integer / integer
        if (checkTypes(a, INTEGER, b, INTEGER)) {
            Long value = getLong(a) / getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // integer / char
        if (checkTypes(a, INTEGER, b, CHAR)) {
            Long value = getLong(a) / getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // char / integer
        if (checkTypes(a, CHAR, b, INTEGER)) {
            Long value = getAsciiCode(a) / getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // integer / boolean
        if (checkTypes(a, INTEGER, b, BOOLEAN)) {
            Long value = getLong(a) / booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean / integer
        if (checkTypes(a, BOOLEAN, b, INTEGER)) {
            Long value = booleanToLong(a) / getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean * boolean
        if (checkTypes(a, BOOLEAN, b, BOOLEAN)) {
            Long value = booleanToLong(a) / booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter * caracter
        if (checkTypes(a, CHAR, b, CHAR)) {
            Long value = getAsciiCode(a) / getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        // caracter * boolean
        if (checkTypes(a, CHAR, b, BOOLEAN)) {
            Long value = getAsciiCode(a) / booleanToLong(b);
            return new Variable(INTEGER, value.toString());
        }

        // boolean * caracter
        if (checkTypes(a, BOOLEAN, b, CHAR)) {
            Long value = booleanToLong(a) / getAsciiCode(b);
            return new Variable(INTEGER, value.toString());
        }

        errorOfTypes(a, b, op, "division");
        return null;
    }

    public Variable mod(Variable a, Variable b, Token op) {
        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, "modulo");
            errorForOperations(b, op, "modulo");
            return null;
        }

        if (checkTypes(a, INTEGER, b, INTEGER)) {
            Long value = getLong(a) % getLong(b);
            return new Variable(INTEGER, value.toString());
        }

        errorOfTypes(a, b, op, "modulo");
        return null;
    }

    public Variable pow(Variable a, Variable b, Token op) {
        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, "potencia");
            errorForOperations(b, op, "potencia");
            return null;
        }

        if (checkTypes(a, INTEGER, b, INTEGER)) {
            Long value = (long) Math.pow(getLong(a), getLong(b));
            return new Variable(INTEGER, value.toString());
        }

        if (checkTypes(a, DOUBLE, b, INTEGER)) {
            Double value = Math.pow(getDouble(a), getLong(b));
            return new Variable(DOUBLE, value.toString());
        }

        errorOfTypes(a, b, op, "potencia");
        return null;
    }

    public Variable uminus(Variable a, Token op) {
        if (a == null) {
            return null;
        }

        if (a.getValue() == null) {
            errorForOperations(a, op, "menos unario");
            return null;
        }

        if (a.getType() == INTEGER) {
            Long value = -getLong(a);
            return new Variable(INTEGER, value.toString());
        }

        if (a.getType() == DOUBLE) {
            Double value = -getDouble(a);
            value = formatDouble(value);
            return new Variable(DOUBLE, value.toString());
        }

        errorOfTypes(a, op, "menos unario");
        return null;
    }

    public Variable logical(Variable a, Variable b, OperationType type, Token op) {
        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, type.toString().toLowerCase());
            errorForOperations(b, op, type.toString().toLowerCase());
            return null;
        }

        if (checkTypes(a, BOOLEAN, b, BOOLEAN)) {
            boolean valueA = getBoolean(a);
            boolean valueB = getBoolean(b);
            Boolean value = true;
            switch (type) {
                case AND:
                    value = valueA && valueB;
                    break;
                case NAND:
                    value = !(valueA && valueB);
                    break;
                case OR:
                    value = valueA || valueB;
                    break;
                case NOR:
                    value = !(valueA || valueB);
                    break;
                case XOR:
                    value = (valueA && !valueB) || (!valueA && valueB);
                    break;
            }
            return new Variable(BOOLEAN, value.toString());
        }

        if (checkTypes(a, BOOLEAN, b, INTEGER) && (b.getValue().equals("1") || b.getValue().equals("0"))) {
            return logical(a, new Variable(BOOLEAN, getBoolean(b).toString()), type, op);
        }

        if (checkTypes(a, INTEGER, b, BOOLEAN) && (a.getValue().equals("1") || b.getValue().equals("0"))) {
            return logical(new Variable(BOOLEAN, getBoolean(a).toString()), b, type, op);
        }

        errorOfTypes(a, b, op, type.toString().toLowerCase());
        return null;
    }

    public Variable not(Variable a, Token op) {
        if (a == null) {
            return null;
        }

        if (a.getValue() == null) {
            errorForOperations(a, op, "negacion logica");
            return null;
        }

        if (a.getType() == BOOLEAN) {
            Boolean value = !getBoolean(a);
            return new Variable(BOOLEAN, value.toString());
        }

        if (a.getType() == INTEGER) {
            if (a.getValue().equals("1")) {
                return new Variable(BOOLEAN, Boolean.TRUE.toString());
            } else if (a.getValue().equals("0")) {
                return new Variable(BOOLEAN, Boolean.FALSE.toString());
            }
        }

        errorOfTypes(a, op, "negacion logica");
        return null;
    }

    public Variable isNull(Variable a) {
        if (a == null) {
            return null;
        }

        Boolean value = a.getValue() == null && a.getArray() == null;
        return new Variable(BOOLEAN, value.toString());
    }

    public Variable compare(Variable a, Variable b, OperationType type, Token op) {
        if (a == null || b == null) {
            return null;
        }

        if (a.getValue() == null || b.getValue() == null) {
            errorForOperations(a, op, "comparacion");
            errorForOperations(b, op, "comparacion");
            return null;
        }

        //  comparing numbers
        if (checkTypes(a, INTEGER, b, DOUBLE) || checkTypes(a, DOUBLE, b, INTEGER) || checkTypes(a, INTEGER, b, INTEGER) || checkTypes(a, DOUBLE, b, DOUBLE)) {
            Boolean value = true;
            Double valueA = getDouble(a), valueB = getDouble(b);
            switch (type) {
                case GREATER:
                    value = valueA > valueB;
                    break;
                case SMALLER:
                    value = valueA < valueB;
                    break;
                case GREATER_OR_EQUAL:
                    value = valueA >= valueB;
                    break;
                case LESS_OR_EQUAL:
                    value = valueA <= valueB;
                    break;
                case EQEQ:
                    value = valueA.doubleValue() == valueB.doubleValue();
                    break;
                case NEQ:
                    value = valueA.doubleValue() != valueB.doubleValue();
                    break;
            }
            return new Variable(BOOLEAN, value.toString());
        }

        // comparing strings
        if (checkTypes(a, STRING, b, STRING)) {
            String valueA = a.getValue();
            String valueB = b.getValue();
            Boolean value = true;
            switch (type) {
                case GREATER:
                    value = valueA.length() > valueB.length();
                    break;
                case SMALLER:
                    value = valueA.length() < valueB.length();
                    break;
                case GREATER_OR_EQUAL:
                    value = valueA.length() >= valueB.length();
                    break;
                case LESS_OR_EQUAL:
                    value = valueA.length() <= valueB.length();
                    break;
                case EQEQ:
                    value = valueA.compareTo(valueB) == 0;
                    break;
                case NEQ:
                    value = valueA.compareTo(valueB) != 0;
                    break;
            }
            return new Variable(BOOLEAN, value.toString());
        }

        // comparing char
        if (checkTypes(a, CHAR, b, CHAR)) {
            Variable a_ = new Variable(INTEGER, getAsciiCode(a).toString());
            Variable b_ = new Variable(INTEGER, getAsciiCode(b).toString());
            return compare(a_, b_, type, op);
        }

        // char comparing integer/double
        if (checkTypes(a, CHAR, b, INTEGER) || checkTypes(a, CHAR, b, DOUBLE)) {
            Variable a_ = new Variable(INTEGER, getAsciiCode(a).toString());
            return compare(a_, b, type, op);
        }

        // comparing integer/double vs char
        if (checkTypes(a, INTEGER, b, CHAR) || checkTypes(a, DOUBLE, b, CHAR)) {
            Variable b_ = new Variable(INTEGER, getAsciiCode(b).toString());
            return compare(a, b_, type, op);
        }

        // comparar boolean vs boolean
        if (checkTypes(a, BOOLEAN, b, BOOLEAN)) {
            String valueA = getBoolean(a).toString();
            String valueB = getBoolean(b).toString();
            Boolean value;
            switch (type) {
                case EQEQ:
                    value = valueA.equals(valueB);
                    return new Variable(BOOLEAN, value.toString());
                case NEQ:
                    value = !valueA.equals(valueB);
                    return new Variable(BOOLEAN, value.toString());
            }
        }

        errorOfTypes(a, b, op, "comparacion");
        return null;
    }

    public boolean getValue(Variable cond) {
        if (cond.getType() == Var.BOOLEAN) {
            String value = cond.getValue().toLowerCase();
            return value.equals("true") || value.equals("verdadero") || value.equals("1");
        }

        if (cond.getType() == Var.INTEGER) {
            if (cond.getValue().equals("1")) {
                return true;
            } else if (cond.getValue().equals("0")) {
                return false;
            }
        }

        System.out.println("While condition no BOOLEAN type");
        return false;
    }

    private boolean checkTypes(Variable a, Var typeA, Variable b, Var typeB) {
        return a.getType() == typeA && b.getType() == typeB;
    }

    private Double getDouble(Variable v) {
        return Double.valueOf(v.getValue());
    }

    private Long getLong(Variable v) {
        return Long.valueOf(v.getValue());
    }

    private Boolean getBoolean(Variable v) {
        String value = v.getValue();
        return value.equalsIgnoreCase("verdadero") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1");
    }

    private Long booleanToLong(Variable v) {
        Boolean value = getBoolean(v);
        return value ? Long.valueOf(1) : Long.valueOf(0);
    }

    private Long getAsciiCode(Variable v) {
        return (long) v.getValue().charAt(0);
    }

    private Long toAsciiCode(Variable v) {
        long value = getLong(v);
        return (long) (value % 255);
    }

    private Double formatDouble(Double value) {
        return Math.round(value * 100_000d) / 100_000d;
    }
}
