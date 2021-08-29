package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.SymbolTable;
import com.cesar31.audiogenerator.instruction.Var;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.parser.Token;
import java.util.Arrays;

/**
 *
 * @author cesar31
 */
public class NativeFunctions {

    OperationHandler handler;

    public NativeFunctions(OperationHandler handler) {
        this.handler = handler;
    }

    public void order(Token id, Token way, SymbolTable table) {
        Variable v = table.getVariable(id.getValue());
        if (v != null) {
            if (v.getArray() != null) {
                int[] dimensions = v.getDimensions();
                if (dimensions.length != 1) {

                    double[] elements;
                    switch (v.getType()) {
                        case INTEGER:
                        case DOUBLE:
                            elements = orderIntegerDoubleChar(v, way);
                            System.out.println(Arrays.toString(elements));
                            break;
                        case CHAR:
                            elements = orderIntegerDoubleChar(v, way);
                            System.out.println(Arrays.toString(elements));
                            break;
                    }

                } else {
                    // No es posible ordenar, retornar 0
                }
            } else {
                // Error, variable no es de tipo arreglo
            }
        } else {
            // Error, variable no existe
        }
    }

    private double[] orderIntegerDoubleChar(Variable v, Token way) {
        double[] elements = getElements(v, v.getType());
        int index = 0;
        String kind = way.getValue().toLowerCase();

        switch (kind) {
            case "ascendente":
                elements = bubblesort(elements, 0, elements.length, true);
                break;
            case "descendente":
                elements = bubblesort(elements, 0, elements.length, false);
                break;
            case "pares":
                index = getCaseFirst(elements, kind);
                elements = bubblesort(elements, 0, index, true);
                elements = bubblesort(elements, index, elements.length, true);
                break;
            case "impares":
                index = getCaseFirst(elements, kind);
                elements = bubblesort(elements, 0, index, true);
                elements = bubblesort(elements, index, elements.length, true);
                break;
            case "primos":
                index = getCaseFirst(elements, kind);
                elements = bubblesort(elements, 0, index, true);
                elements = bubblesort(elements, index, elements.length, true);
                break;
        }

        return elements;
    }

    private int getCaseFirst(double[] elements, String kind) {
        int i = 0;
        for (int j = 0; j < elements.length; j++) {
            int number = (int) Math.floor(elements[j]);
            if (testCase(number, kind)) {
                double aux = elements[j];
                elements[j] = elements[i];
                elements[i] = aux;
                i++;
            }
        }

        return i;
    }

    private boolean testCase(int number, String kind) {
        switch (kind) {
            case "pares":
                return number % 2 == 0;
            case "impares":
                return number % 2 == 1;
            case "primos":
                int count = 1;
                int divisor = 2;
                while (count <= 2 && divisor <= number) {
                    if (number % divisor == 0) {
                        count++;
                    }
                    divisor++;
                }
                return count <= 2;
        }

        return false;
    }

    private double[] getElements(Variable v, Var kind) {
        double[] elements = new double[v.getDimensions()[0]];
        for (int i = 0; i < v.getDimensions()[0]; i++) {
            int[] a = {i};
            Object value = handler.getArray().getValueFromArray(a, v.getArray());
            switch (kind) {
                case INTEGER:
                case DOUBLE:
                    double val = Double.valueOf(value.toString());
                    elements[i] = val;
                    break;
                case CHAR:
                    int aux = (int) value.toString().charAt(0);
                    elements[i] = (double) aux;
            }
        }

        return elements;
    }

    private double[] bubblesort(double[] list, int start, int end, boolean asc) {

        for (int i = 1; i < end; i++) {
            for (int j = start; j < end - i; j++) {
                if (asc) {
                    if (list[j] > list[j + 1]) {
                        double aux = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = aux;
                    }
                } else {
                    if (list[j] < list[j + 1]) {
                        double aux = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = aux;
                    }
                }
            }
        }

        return list;
    }

    /**
     * Double
     *
     * @param list
     * @return
     */
    private String add(double[] list) {
        Double result = 0d;
        for (double d : list) {
            result += d;
        }
        return result.toString();
    }

    /**
     * Cadenas
     *
     * @param list
     * @return
     */
    private String add(String[] list) {
        String result = "";
        for (String s : list) {
            result += s;
        }
        return result;
    }

    /**
     * Caracteres
     *
     * @param list
     * @return
     */
    private String add(char[] list) {
        String result = "";
        for (char c : list) {
            result += c;
        }
        return result;
    }

    /**
     * Listado de enteros
     *
     * @param list
     * @return
     */
    private String add(long[] list) {
        int val = 0;

        for (long i : list) {
            val += i;
        }
        return String.valueOf(val);
    }
}
