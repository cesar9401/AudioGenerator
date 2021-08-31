package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.Operation;
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

    /**
     * Ordenar enteros/dobles/char
     *
     * @param list
     * @param start
     * @param end
     * @param asc
     * @return
     */
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
     * Ordenar cadenas
     *
     * @param list
     * @param start
     * @param end
     * @param asc
     * @return
     */
    private String[] bubblesort(String[] list, int start, int end, boolean asc) {
        for (int i = 1; i < end; i++) {
            for (int j = start; j < end - i; j++) {
                if (asc) {
                    if (list[j].compareToIgnoreCase(list[j + 1]) > 0) {
                        String aux = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = aux;
                    }
                } else {
                    if (list[j].compareToIgnoreCase(list[j + 1]) < 0) {
                        String aux = list[j];
                        list[j] = list[j + 1];
                        list[j + 1] = aux;
                    }
                }
            }
        }

        return list;
    }

    public Variable order(Operation operation, Token way, Token info, SymbolTable table) {
        Variable v = operation.run(table, handler);
        if (v != null) {
            if (v.getArray() != null) {
                int[] dimensions = v.getDimensions();
                if (dimensions.length == 1) {
                    double[] elements;
                    switch (v.getType()) {
                        case INTEGER:
                            elements = orderIntegerDoubleChar(v, way, info);
                            if (elements != null) {
                                String[] array = Arrays.stream(elements).mapToObj(val -> String.valueOf(Double.valueOf(val).longValue())).toArray(String[]::new);
                                // insertar elementos en arreglo
                                setElementsArray(v, array);
                                // System.out.println(Arrays.toString(array));
                                return new Variable(Var.INTEGER, "1");
                            }
                            return new Variable(Var.INTEGER, "0");
                        case DOUBLE:
                            elements = orderIntegerDoubleChar(v, way, info);
                            if (elements != null) {
                                String[] array1 = Arrays.stream(elements).mapToObj(String::valueOf).toArray(String[]::new);
                                // insertar elementos en arreglo
                                setElementsArray(v, array1);
                                // System.out.println(Arrays.toString(array1));
                                return new Variable(Var.INTEGER, "1");

                            }
                            return new Variable(Var.INTEGER, "0");
                        case CHAR:
                            elements = orderIntegerDoubleChar(v, way, info);
                            if (elements != null) {
                                String[] array2 = Arrays.stream(elements).mapToObj(val -> String.valueOf((char) Double.valueOf(val).intValue())).toArray(String[]::new);
                                setElementsArray(v, array2);
                                // System.out.println(Arrays.toString(array2));
                                return new Variable(Var.INTEGER, "1");
                            }
                            return new Variable(Var.INTEGER, "0");
                        case STRING:
                            String[] string = getStringElements(v, info, "Ordenar");
                            if (string != null) {
                                switch (way.getValue().toLowerCase()) {
                                    case "ascendente":
                                    case "descendente":
                                        string = orderStrings(string, way);
                                        // insertar elementos al arreglo
                                        setElementsArray(v, string);
                                        // System.out.println(Arrays.toString(string));
                                        return new Variable(Var.INTEGER, "1");
                                    default:
                                        return new Variable(Var.INTEGER, "0");
                                }
                            }
                            return new Variable(Var.INTEGER, "0");
                        case BOOLEAN:
                            String[] bool = getStringElements(v, info, "Ordenar");
                            if (bool != null) {
                                switch (way.getValue().toLowerCase()) {
                                    case "ascendente":
                                    case "descendente":
                                        bool = orderBoolean(bool, way);
                                        setElementsArray(v, bool);
                                        // System.out.println(Arrays.toString(bool));
                                        return new Variable(Var.INTEGER, "1");
                                    default:
                                        return new Variable(Var.INTEGER, "0");
                                }
                            }
                            return new Variable(Var.INTEGER, "0");
                    }
                } else {
                    return new Variable(Var.INTEGER, "0");
                    // No es posible ordenar, retornar 0
                }
            } else {
                // Error, variable no es de tipo arreglo
            }
        } else {
            // Error, variable no existe
        }

        return new Variable(Var.INTEGER, "0");
    }

    private void setElementsArray(Variable v, String[] elements) {
        for (int i = 0; i < elements.length; i++) {
            int[] a = {i};
            handler.getArray().setValueToArray(a, elements[i], v.getArray());
        }
    }

    /**
     * Aplicar ordenamiento segun casos (Enteros, double, char)
     *
     * @param v
     * @param way
     * @return
     */
    private double[] orderIntegerDoubleChar(Variable v, Token way, Token info) {
        double[] elements = getElements(v, v.getType(), info);

        if (elements == null) {
            return null;
        }

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

    private String[] orderStrings(String[] list, Token way) {
        String kind = way.getValue().toLowerCase();
        switch (kind) {
            case "ascendente":
                list = bubblesort(list, 0, list.length, true);
                break;
            case "descendente":
                list = bubblesort(list, 0, list.length, false);
                break;
        }

        return list;
    }

    private String[] orderBoolean(String[] list, Token way) {
        int index = getCaseFirst(list);
        String kind = way.getValue().toLowerCase();
        switch (kind) {
            case "ascendente":
                list = bubblesort(list, 0, index, true);
                list = bubblesort(list, index, list.length, true);
                break;
            case "descendente":
                list = bubblesort(list, 0, index, true);
                list = bubblesort(list, index, list.length, true);
                int i = 0,
                 j = list.length - 1;
                while (i <= j) {
                    String aux = list[i];
                    list[i] = list[j];
                    list[j] = aux;
                    i++;
                    j--;
                }
                break;
        }

        return list;
    }

    /**
     * Obtener indice para agregar los del caso(primos, pares, impares) al
     * inicio y el indice indica donde terminan
     *
     * @param elements
     * @param kind
     * @return
     */
    private int getCaseFirst(double[] elements, String kind) {
        int i = 0;
        for (int j = 0; j < elements.length; j++) {
            long number = (long) Math.floor(Math.abs(elements[j]));
            if (testCase(number, kind)) {
                double aux = elements[j];
                elements[j] = elements[i];
                elements[i] = aux;
                i++;
            }
        }

        return i;
    }

    private int getCaseFirst(String[] elements) {
        int i = 0;
        for (int j = 0; j < elements.length; j++) {
            String w = elements[j];
            boolean value = w.equalsIgnoreCase("true") || w.equalsIgnoreCase("verdadero") || w.equalsIgnoreCase("1");
            if (!value) {
                String aux = elements[j];
                elements[j] = elements[i];
                elements[i] = aux;
                i++;
            }
        }

        return i;
    }

    private boolean testCase(long number, String kind) {
        switch (kind) {
            case "pares":
                return number % 2 == 0;
            case "impares":
                return number % 2 == 1;
            case "primos":
                long count = 1;
                long divisor = 2;
                number = number < 0 ? -number : number;
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

    private double[] getElements(Variable v, Var kind, Token info) {
        boolean error = false;
        double[] elements = new double[v.getDimensions()[0]];
        for (int i = 0; i < v.getDimensions()[0]; i++) {
            int[] a = {i};
            Object value = handler.getArray().getValueFromArray(a, v.getArray());
            if (value != null) {
                switch (kind) {
                    case DOUBLE:
                        double val = Double.valueOf(value.toString());
                        elements[i] = val;
                        break;
                    case CHAR:
                        int aux = (int) value.toString().charAt(0);
                        elements[i] = (double) aux;
                        break;
                    case INTEGER:
                        double val1 = Double.valueOf(value.toString());
                        elements[i] = val1;
                        break;
                }
            } else {
                error = true;
                // Crear error aqui
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), v.getId());
                String description = "En la llamada a la funcion `Ordenar`, al arreglo `" + v.getId() + "`, en la direccion `" + Arrays.toString(a) + "` tiene un valor null. No es posible seguir con la operacion";
                err.setDescription(description);
                handler.getErrors().add(err);
            }
        }

        if (error) {
            return null;
        }

        return elements;
    }

    /**
     * Obtener elementos de tipo double para sumarizacion
     *
     * @param v
     * @return
     */
    private double[] getDoubleElements(Variable v, Token info) {
        double[] elements = new double[v.getDimensions()[0]];
        boolean error = false;
        for (int i = 0; i < elements.length; i++) {
            int[] a = {i};
            Object value = handler.getArray().getValueFromArray(a, v.getArray());
            if (value != null) {
                elements[i] = Double.valueOf(value.toString());
            } else {
                error = true;
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), v.getId());
                String description = "En la llamada a la funcion sumarizar, el arreglo `" + v.getId() + "` en la direccion `" + Arrays.toString(a) + "` tiene un valor null, no es posible efectuar la operacion.";
                err.setDescription(description);
                handler.getErrors().add(err);
            }
        }
        if (error) {
            return null;
        }

        return elements;
    }

    /**
     * Obtener elementos de tipo String para sumarizacion/ordenar
     *
     * @param v
     * @return
     */
    private String[] getStringElements(Variable v, Token info, String function) {
        String[] elements = new String[v.getDimensions()[0]];
        boolean error = false;
        for (int i = 0; i < elements.length; i++) {
            int[] a = {i};
            Object value = handler.getArray().getValueFromArray(a, v.getArray());
            if (value != null) {
                elements[i] = value.toString();
            } else {
                error = true;
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), v.getId());
                String description = "En la llamada a la funcion " + function + ", el arreglo `" + v.getId() + "` en la direccion `" + Arrays.toString(a) + "` tiene un valor null, no es posible efectuar la operacion.";
                err.setDescription(description);
                handler.getErrors().add(err);
            }
        }
        if (error) {
            return null;
        }

        return elements;
    }

    /**
     * Obtener elementos de tipo Integer para sum
     *
     * @param v
     * @return
     */
    private long[] getIntegerElements(Variable v, Token info) {
        boolean error = false;
        long[] elements = new long[v.getDimensions()[0]];
        for (int i = 0; i < elements.length; i++) {
            int[] a = {i};
            Object value = handler.getArray().getValueFromArray(a, v.getArray());
            if (value != null) {
                elements[i] = Long.valueOf(value.toString());
            } else {
                error = true;
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), v.getId());
                String description = "En la llamada a la funcion sumarizar, el arreglo `" + v.getId() + "` en la direccion `" + Arrays.toString(a) + "` tiene un valor null, no es posible efectuar la operacion.";
                err.setDescription(description);
                handler.getErrors().add(err);
            }
        }
        if (error) {
            return null;
        }

        return elements;
    }

    public Variable getSummarize(Variable val, Token info) {
        String result = "";
        switch (val.getType()) {
            case DOUBLE:
                double[] listD = getDoubleElements(val, info);
                if (listD == null) {
                    return null;
                }
                result = add(listD);
                break;
            case INTEGER:
                long[] list = getIntegerElements(val, info);
                if (list == null) {
                    return null;
                }
                result = add(list);
                break;
            case BOOLEAN:
            case CHAR:
            case STRING:
                String[] list1 = getStringElements(val, info, "sumarizar");
                if (list1 == null) {
                    return null;
                }
                result = add(list1);
                break;
        }

        return new Variable(Var.STRING, result);
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

    private String add(long[] list) {
        Long result = 0l;
        for (long i : list) {
            result += i;
        }
        return result.toString();
    }

    /**
     * Cadenas / Caracteres / Booleanos
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
}
