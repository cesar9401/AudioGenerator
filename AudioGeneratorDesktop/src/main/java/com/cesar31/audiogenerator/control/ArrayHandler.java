package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.*;
import com.cesar31.audiogenerator.parser.Token;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ArrayHandler {

    private CastHandler cast;

    public ArrayHandler() {
        this.cast = new CastHandler();
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
            Var kind = cast.getType(type);
            Object array = Array.newInstance(String.class, dimensions);
            Variable variable = new Variable(kind, id.getValue(), keep, dimensions, array);
            if (!e.containsVariable(variable.getId())) {
                e.add(variable);
            } else {
                System.out.println("El id ya fue declarado");
            }
        }
    }

    /**
     * Agregar declaracion e inicializacion de arreglos a tabla de simbolos
     *
     * @param type token tipo del arreglo
     * @param id token con el id del arreglo
     * @param keep variable keep
     * @param dimensions dimensiones del arreglo
     * @param value objecto con valores del arreglo
     * @param ind dimension de cada arreglo individual
     * @param e tabla de simbolos
     */
    public void addArrayAssignmentToSymbolTable(Token type, Token id, boolean keep, int[] dimensions, Object value, List<Integer> ind, SymbolTable e) {
        if (type != null) {
            Var kind = cast.getType(type);

            //Verificar que dimensiones de value sean las mismas que las declaradas y almacenadas en dimensions
            if (checkArrayDimensions(dimensions, ind)) {
                int[] aux = new int[dimensions.length];
                Object item = getValueFromArray(aux, value);
                if (item != null && isNotAnArray(item)) {
                    // En este punto las dimensiones son correctas
                    // se debe verificar que los tipos de las variables del arreglo coincidan con el tipo de array declarado

                    // obtener indices de arreglo
                    List<int[]> indexes = new ArrayList<>();
                    aux = new int[dimensions.length];
                    arrayTour(dimensions.length - 1, 0, dimensions, aux, new HashMap<>(), indexes);

                    List<String> values = getValuesForArray(indexes, kind, value, e);
                    Object array = Array.newInstance(String.class, dimensions);

                    // Agregar elementos al arreglo
                    for (int i = 0; i < indexes.size(); i++) {
                        setValueToArray(indexes.get(i), values.get(i), array);
                    }

                    // Crear variable aqui y agregar a tabla de simbolos
                    Variable matrix = new Variable(kind, id.getValue(), keep, dimensions, array);
                    if (!e.containsVariable(matrix.getId())) {
                        e.add(matrix);
                    } else {
                        System.out.println("Error, ya se encuentra arreglo en tabla de simbolos");
                    }
                }
            }
        }
    }

    public Variable getItemFromArray(Token id, List<Operation> indexes, SymbolTable e) {
        Variable v = e.getVariable(id.getValue());
        if (v != null) {
            if (v.getArray() != null) {
                int[] index = new int[indexes.size()];
                boolean error = false;

                for (int i = 0; i < indexes.size(); i++) {
                    Variable tmp = indexes.get(i).run(e);

                    if (tmp != null) {
                        if (tmp.getType() == Var.INTEGER) {
                            index[i] = Integer.valueOf(tmp.getValue());
                            if (index[i] < 0) {
                                // Error, indice menor que cero
                                error = true;
                            }
                        } else {
                            error = true;
                            // Error, variable para acceder a indice i no es de tipo entero
                        }
                    } else {
                        error = true;
                        // Error, variable con que intenta accesar a indice i no existe
                    }
                }

                if (!error) {
                    // verificar que los indices no sobrepasen a la longitud del array
                    if (rightDimensions(v.getDimensions(), index)) {
                        Object value = this.getValueFromArray(index, v.getArray());
                        if (value != null) {
                            return new Variable(v.getType(), value.toString());
                        } else {
                            // Error, no tiene valor definido en indices index
                            System.out.println("error, no tiene valor def");
                        }

                    } else {
                        // out of indexs
                        System.out.println("Ouf of indexs");
                    }
                }

            } else {
                // Error, variable no es de tipo arreglo
                System.out.println("variable no es de tipo arreglo");
            }
        } else {
            // Error, variable no existe
            System.out.println("Variable arreglo no existe");
        }

        return null;
    }

    private List<String> getValuesForArray(List<int[]> indexes, Var kind, Object value, SymbolTable e) {
        List<String> values = new ArrayList<>();

        for (int[] i : indexes) {
            Object obj = getValueFromArray(i, value);
            if (obj != null) {
                Operation op = (Operation) obj;
                Variable v = op.run(e);
                Variable variable = cast.typeConversion(kind, v);
                values.add(variable.getValue());
            }
        }

        return values;
    }

    /**
     * Metodo para obtener los indices para cada valor del arreglo declarado
     *
     * @param total (dimension - 1) del arreglo
     * @param current dimension actual
     * @param dimensions arreglo con las dimensiones del arreglo
     * @param aux arreglo con las mismas dimensiones del arreglo (vacio)
     * @param map
     * @param indexes lista para almacenar los arreglos
     */
    private void arrayTour(int total, int current, int[] dimensions, int[] aux, HashMap<String, String> map, List<int[]> indexes) {
        for (int i = 0; i < dimensions[current]; i++) {
            aux[current] = i;

            if (current < total) {
                arrayTour(total, current + 1, dimensions, aux, map, indexes);
            }

            String tmp = Arrays.toString(aux);
            if (!map.containsKey(tmp)) {
                map.put(tmp, tmp);

                int[] index = Arrays.copyOf(aux, aux.length);
                indexes.add(index);
                //System.out.println(Arrays.toString(index));
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
        if (size == 1 && dimensions.length == 1) {
            return true;
        } else if (size == 1 && dimensions.length != 1) {
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
            } else if (size == 1) {
                return false;
            }
        }

        return false;
    }

    /**
     * Obtener elemento en especifico de un array
     *
     * @param dimension la dimension donde se encuentra el element
     * @param array el arreglo del cual se quiere obtener el item
     * @return item or null
     */
    private Object getValueFromArray(int[] dimension, Object array) {
        try {
            int i = 1;
            Object tmp = Array.get(array, dimension[0]);
            while (i < dimension.length) {
                tmp = Array.get(tmp, dimension[i]);
                i++;
            }
            return tmp;
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            //e.printStackTrace(System.out);
            return null;
        }
    }

    private void setValueToArray(int[] dimension, String value, Object array) {
        int i = 0;
        Object aux = array;
        while (i < dimension.length - 1) {
            aux = Array.get(aux, dimension[i]);
            i++;
        }
        Array.set(aux, dimension[i], value);
    }

    private boolean isNotAnArray(Object item) {
        try {
            Array.getLength(item);
            return false;
        } catch (IllegalArgumentException e) {
            // e.printStackTrace(System.out);
            return true;
        }
    }

    private boolean rightDimensions(int[] length, int[] indexes) {
        if (length.length != indexes.length) {
            return false;
        }

        for (int i = 0; i < length.length; i++) {
            if (indexes[i] >= length[i]) {
                return false;
            }
        }

        return true;
    }
}
