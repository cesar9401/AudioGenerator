package com.cesar31.audiogenerator.control;

import static com.cesar31.audiogenerator.instruction.Var.*;
import com.cesar31.audiogenerator.instruction.*;
import com.cesar31.audiogenerator.parser.Token;
import java.lang.reflect.Array;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ArrayHandler {

    public ArrayHandler() {

    }

    /**
     * Agregar declaracion de array a tabla de simbolos
     *
     * @param type
     * @param id
     * @param keep
     * @param dimensions
     * @param e
     */
    public void addArrayStatementToSymbolTable(Token type, Token id, boolean keep, int[] dimensions, SymbolTable e) {
        if (type != null) {
            Var kind = getType(type);
            Object array = Array.newInstance(String.class, dimensions);
            Variable variable = new Variable(kind, id.getValue(), keep, dimensions, array);
            if (!e.containsVariable(variable.getId())) {
                e.add(variable);
            } else {
                System.out.println("El id ya fue declarado");
            }
        }
    }

    public void addArrayAssignmentTSymbolTable(Token type, Token id, boolean keep, int[] dimensions, Object value, List<Integer> ind, SymbolTable e) {
        if (type != null) {
            Var kind = getType(type);

            //Verificar que dimensiones de value sean las mismas que las declaradas y almacenadas in dimensions
            boolean dimOk = checkArrayDimensions(dimensions, ind);
            if(dimOk) {
                System.out.println("dimensiones correctas " + id.getValue());
                int[] aux = new int[dimensions.length];
                
            }
        }
    }

    private boolean checkArrayDimensions(int[] dimensions, List<Integer> ind) {
        int first = dimensions[dimensions.length - 1];
        for (int i = 0; i < ind.size(); i++) {
            if (first != ind.get(i)) {
                System.out.println("Error, las dimensiones no coinciden");
                return false;
            }
        }

        int size = ind.size();
        if(size == 1 && dimensions.length == 1) {
            return true;
        } else if(size == 1 && dimensions.length != 1) {
            return false;
        }
        
        for (int i = dimensions.length - 2; i >= 0; i--) {
            if (size % dimensions[i] == 0) {
                size /= dimensions[i];
            } else {
                // Error
                System.out.println("Dimensiones no coinciden!");
                return false;
            }

            if (i == 0 && size == 1) {
                return true;
            } else if(size == 1) {
                return false;
            }
        }

        return false;
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
}
