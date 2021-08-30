package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
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

    private OperationHandler handler;

    public ArrayHandler(OperationHandler handler) {
        this.handler = handler;
    }

    private void errorArrayAssignment(ArrayAccess arrayItem, Operation operation, Var var) {
        Token op = operation.getOp();
        Err err = new Err(Err.TypeErr.SINTACTICO, op.getLine(), op.getColumn(), op.getValue());
        String description = "La asignacion definida por el operador `" + op.getValue() + "` no es aplicable a variables de tipo `" + var.getName() + "`, como lo es la variable `" + arrayItem.getId().getValue() + "`.";
        err.setDescription(description);
        this.handler.getErrors().add(err);
    }

    private void errorArrayAssignmentWithNoValue(ArrayAccess arrayItem, Operation operation, int[] index) {
        Token t = arrayItem.getId();
        Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
        String description = "En la asignacion definida por el operador `" + operation.getOp().getValue() + "` el arreglo en la direccion `" + Arrays.toString(index) + "` tiene valor nulo, por tanto no es posible realizar tal operacion.";
        err.setDescription(description);
        this.handler.getErrors().add(err);
    }

    public void assignElementToArray(ArrayAccess arrayItem, Operation operation, Assignment.TypeA type, SymbolTable table) {
        Variable tmp = arrayItem.run(table, handler);
        if (tmp != null) {
            // Obtener indices aqui
            int[] indexes = new int[arrayItem.getIndexes().size()];
            for (int i = 0; i < indexes.length; i++) {
                // En este punto, los tipos ya fueron comprobados
                Variable aux = arrayItem.getIndexes().get(i).getOperation().run(table, handler);
                indexes[i] = Integer.valueOf(aux.getValue());
            }

            switch (type) {
                case EQUAL:
                    // do nothing
                    break;
                case MINUS_MINUS:
                    if (tmp.getType() == Var.BOOLEAN || tmp.getType() == Var.STRING) {
                        errorArrayAssignment(arrayItem, operation, tmp.getType());
                        return;
                    }

                    if (tmp.getValue() == null) {
                        errorArrayAssignmentWithNoValue(arrayItem, operation, indexes);
                        return;
                    }

                    break;
                case PLUS_PLUS:
                    if (tmp.getType() == Var.BOOLEAN) {
                        errorArrayAssignment(arrayItem, operation, tmp.getType());
                        return;
                    }

                    if (tmp.getValue() == null) {
                        errorArrayAssignmentWithNoValue(arrayItem, operation, indexes);
                        return;
                    }

                    break;
                case PLUS_EQ:
                    if (tmp.getType() == Var.BOOLEAN) {
                        errorArrayAssignment(arrayItem, operation, tmp.getType());
                        return;
                    }

                    if (tmp.getValue() == null) {
                        errorArrayAssignmentWithNoValue(arrayItem, operation, indexes);
                        return;
                    }
                    break;
            }

            // Valor a asignar
            Variable value = operation.run(table, handler);

            // Arreglo
            Variable array = table.getVariable(arrayItem.getId().getValue());

            if (value != null) {
                if (value.getValue() != null) {
                    Variable newType = handler.getCast().typeConversion(tmp.getType(), value);
                    if (newType != null) {

                        // Asignacion
                        setValueToArray(indexes, newType.getValue(), array.getArray());

                    } else {
                        // Error, no es posible asignar de del tipo de value al tipo de tmp
                        Token t = arrayItem.getId();

                        Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                        String description = "No es posible realizar una asignacion al arreglo `" + array.getId() + "` de tipo `" + array.getType().getName() + "`";
                        description += " y el valor a asignar en la dimension `" + Arrays.toString(indexes) + "` con valor `" + value.getValue() + "` de tipo `" + value.getType().getName() + "`.";
                        err.setDescription(description);
                        this.handler.getErrors().add(err);
                    }
                } else {
                    // Error, variable no tiene valor definido
                    Token t = arrayItem.getId();
                    Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                    String description = "Se esta intetando asignar un valor nulo al arreglo en la direccion `" + Arrays.toString(indexes) + "`, ";
                    if (value.getId() != null) {
                        description += "Esto se debe a que la variable `" + value.getId() + "`, no tiene un valor definido.";
                        err.setLexema(value.getId());
                        if (value.getToken() != null) {
                            err.setLine(value.getToken().getLine());
                            err.setColumn(value.getToken().getColumn());
                        }
                    } else {
                        description += "Esto se debe a que probablemente uno de los operadores a asignar no tiene un valor definido.";
                    }
                    err.setDescription(description);
                    handler.getErrors().add(err);
                }
            } else {
                // Errores se verifican cuando value = operation.run(table, handler)
                Token t = arrayItem.getId();
                Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                String description = "Se esta intetando asignar un valor nulo al arreglo en la direccion `" + Arrays.toString(indexes) + "`, ";
                description += "Esto se debe a que probablemente uno de los operadores a asignar no tiene un valor definido.";
                err.setDescription(description);
                handler.getErrors().add(err);
            }
        } else {
            // Errores se verifican cuando -> arrayItem.run(table, handler)
            // Colocar error aqui
            System.out.println("Errores del areglo");
        }
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
            Var kind = handler.getCast().getType(type);

            Variable variable;
            if (handler.isTest()) {
                Object array = new Object();
                variable = new Variable(kind, id.getValue(), keep, dimensions, array);
            } else {
                Object array = Array.newInstance(String.class, dimensions);
                variable = new Variable(kind, id.getValue(), keep, dimensions, array);
            }

            if (!e.containsVariable(variable.getId())) {
                e.add(variable);
            } else {
                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                String description = "El id que desea utilizar `" + id.getValue() + "`, ya ha sido usada en este ambito, intente con otro identificador.";
                err.setDescription(description);
                handler.getErrors().add(err);
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
            Var kind = handler.getCast().getType(type);

            if (handler.isTest()) {
                Variable matrix = new Variable(kind, id.getValue(), keep, dimensions, new Object());
                if (!e.containsVariable(matrix.getId())) {
                    e.add(matrix);
                } else {
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "La variable `" + id.getValue() + "` ya esta definida en este ambito, intente con otro identificador.";
                    err.setDescription(description);
                    this.handler.getErrors().add(err);
                }
                return;
            }

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

                    List<String> values = getValuesForArray(id, indexes, kind, value, e, handler);

                    if (values != null) {
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
                            Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                            String description = "La variable `" + id.getValue() + "` ya esta definida en este ambito, intente con otro identificador.";
                            err.setDescription(description);
                            this.handler.getErrors().add(err);
                        }
                    }

                } else {
                    // dimensiones no son correctas
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "En la declaracion del arreglo `" + id.getValue() + "`, las dimensiones declaradas para el arreglo: `" + Arrays.toString(dimensions) + "`, no coinciden con las dimensiones del arreglo a asignar.";
                    err.setDescription(description);
                    handler.getErrors().add(err);
                }
            } else {
                // dimensiones no son correctas
                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                String description = "En la declaracion del arreglo `" + id.getValue() + "`, las dimensiones declaradas para el arreglo: `" + Arrays.toString(dimensions) + "`, no coinciden con las dimensiones del arreglo a asignar.";
                err.setDescription(description);
                handler.getErrors().add(err);
            }
        }
    }

    /**
     * Obtener un elemento en especifico de un arreglo, metodo principal para el
     * acceso de un arreglo
     *
     * @param id Token con el nombre del id
     * @param indexes Listado de instrucciones con los indices para el acceso al
     * arreglo
     * @param e tabla de simbolos
     * @return
     */
    public Variable getItemFromArray(Token id, List<ArrayIndex> indexes, SymbolTable e) {
        Variable v = e.getVariable(id.getValue());
        if (v != null) {
            if (v.getArray() != null) {
                int[] index = new int[indexes.size()];
                boolean error = false;

                for (int i = 0; i < indexes.size(); i++) {
                    Variable tmp = indexes.get(i).getOperation().run(e, handler);

                    if (tmp != null) {
                        if (tmp.getType() == Var.INTEGER) {
                            if (!handler.isTest()) {
                                index[i] = Integer.valueOf(tmp.getValue());
                                if (index[i] < 0) {
                                    // Error, indice menor que cero
                                    error = true;
                                    Token left = indexes.get(i).getLbracket();

                                    Err err = new Err(Err.TypeErr.SINTACTICO, left.getLine(), left.getColumn() + 1, "");
                                    String description = "En la llamada al arreglo " + id.getValue() + ", el indice numero " + (i + 1);
                                    if (tmp.getId() != null) {
                                        description += " con id: `" + tmp.getId() + "`";
                                        err.setLexema(tmp.getId());
                                        if (tmp.getToken() != null) {
                                            err.setLine(tmp.getToken().getLine());
                                            err.setColumn(tmp.getToken().getColumn());
                                        }
                                    }
                                    description += ", con valor entero(value = `" + tmp.getValue() + "` no es valido para definir la longitud/dimension de un arreglo. Este debe ser mayor que cero.";
                                    err.setDescription(description);
                                    handler.getErrors().add(err);
                                }
                            }

                        } else {
                            // Error, variable para acceder a indice i no es de tipo entero
                            error = true;
                            Token left = indexes.get(i).getLbracket();

                            Err err = new Err(Err.TypeErr.SINTACTICO, left.getLine(), left.getColumn() + 1, "");
                            String description = "En la llamada al arreglo " + id.getValue() + ", el indice numero " + (i + 1);
                            if (tmp.getId() != null) {
                                description += " con id: `" + tmp.getId() + "`";
                                err.setLexema(tmp.getId());
                                if (tmp.getToken() != null) {
                                    err.setLine(tmp.getToken().getLine());
                                    err.setColumn(tmp.getToken().getLine());
                                }
                            }
                            description += ", no es de tipo entero(value = `" + tmp.getValue() + "`, tipo = `" + tmp.getType().getName() + "`). Se necesita una variable de tipo entero para definir la longitud/dimension de un arreglo.";
                            err.setDescription(description);
                            handler.getErrors().add(err);

                        }
                    } else {
                        error = true;
                        // Error, variable con que intenta accesar a indice i no existe, se verifica cuando se hace la operacion y el posible id no existe
                    }
                }

                if (!error) {
                    // verificar que los indices no sobrepasen a la longitud del array
                    if (v.getDimensions().length == index.length) {
                        if (handler.isTest()) {
                            return new Variable(v.getType(), "");
                        } else {
                            if (rightDimensions(v.getDimensions(), index)) {
                                Object value = this.getValueFromArray(index, v.getArray());
                                if (value != null) {
                                    // Elemento de arreglo
                                    Variable aux = new Variable(v.getType(), value.toString());
                                    aux.setId(v.getId());
                                    aux.setDimensions(index);
                                    return aux;
                                } else {
                                    // Elemento de arreglo sin valor
                                    Variable aux = new Variable(v.getType(), null);
                                    aux.setId(v.getId());
                                    aux.setDimensions(index);
                                    return aux;
                                }

                            } else {
                                // out of indexs
                                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), "");
                                String description = "En la llamada al arreglo `" + id.getValue() + "`, los indices indicados: `" + Arrays.toString(index) + "`, estan fuera de rango.";
                                err.setDescription(description);
                                handler.getErrors().add(err);
                            }
                        }
                    } else {
                        // error, dimensiones con concuerdan
                        Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), "");
                        String description = "En la llamada el arreglo `" + id.getValue() + "`, los indices indicados no concuerdan con las dimensiones del arreglo.";
                        description += " El arreglo es de " + v.getDimensions().length + " dimensiones, y en la llamada se han indicado: " + index.length + ".";
                        err.setDescription(description);
                        handler.getErrors().add(err);
                    }
                }

            } else {
                // Error, variable no es de tipo arreglo
                Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                String description = "La variable con identificador `" + id.getValue() + "`, no corresponde a una variable de tipo arreglo.";
                err.setDescription(description);
                this.handler.getErrors().add(err);
            }
        } else {
            // Error, variable no existe
            Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
            String description = "El arreglo con identificador `" + id.getValue() + "` no ha sido declarado.";
            err.setDescription(description);
            this.handler.getErrors().add(err);
            
            return null;
        }

        return null;
    }

    /**
     * Obtener elementos del arreglo que se obtuvieron en la declaracion del
     * mismo
     *
     * @param id
     * @param indexes
     * @param kind
     * @param value
     * @param e
     * @param handler
     * @return
     */
    private List<String> getValuesForArray(Token id, List<int[]> indexes, Var kind, Object value, SymbolTable e, OperationHandler handler) {
        List<String> values = new ArrayList<>();
        boolean error = false;

        for (int[] i : indexes) {
            Object obj = getValueFromArray(i, value);
            if (obj != null) {
                Operation op = (Operation) obj;
                Variable v = op.run(e, handler);
                if (v != null) {
                    Variable variable = handler.getCast().typeConversion(kind, v);
                    if (variable != null) {
                        values.add(variable.getValue());
                    } else {
                        // Error, el elemento en el indice i no se puede agregar al arreglo por ser de un tipo distinto
                        Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                        String description = "No es posible realizar una asignacion al arreglo `" + id.getValue() + "` de tipo `" + kind.getName() + "`";
                        description += " y el valor a asignar en el indice`" + Arrays.toString(i) + "` con valor `" + v.getValue() + "`, de tipo `" + v.getType().getName() + "`.";
                        err.setDescription(description);
                        this.handler.getErrors().add(err);
                        error = true;
                    }
                } else {
                    // Error, variable null, probablemente uno de los operadores tiene un valor nulo
                    Err err = new Err(Err.TypeErr.SINTACTICO, id.getLine(), id.getColumn(), id.getValue());
                    String description = "No es posible realizar una asignacion al arreglo `" + id.getValue() + "` en el indice `" + Arrays.toString(i) + "` de un valor null";
                    description += ", esto se debe a que probablemente uno de los operadores a asignar no tiene un valor definido.";
                    err.setDescription(description);
                    this.handler.getErrors().add(err);

                    error = true;
                }
            }
        }

        if (error) {
            return null;
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
                // System.out.println("Error, las dimensiones no coinciden");
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
                // System.out.println("Dimensiones no coinciden!");
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
    public Object getValueFromArray(int[] dimension, Object array) {
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

    /**
     * Asignar un elemento al arreglo
     *
     * @param dimension lugar donde se guarda o agrega el elemento
     * @param value valor a guardar en el arreglo
     * @param array es el arreglo
     */
    private void setValueToArray(int[] dimension, String value, Object array) {
        int i = 0;
        Object aux = array;
        while (i < dimension.length - 1) {
            aux = Array.get(aux, dimension[i]);
            i++;
        }
        Array.set(aux, dimension[i], value);
    }

    /**
     * Metodo para verificar si en la ultima dimension de un arreglo no se
     * guarda otro arreglo
     *
     * @param item al objeto a verificar
     * @return retorna true si el elemento no es un arreglo, y false en caso
     * contrario
     */
    private boolean isNotAnArray(Object item) {
        try {
            Array.getLength(item);
            return false;
        } catch (IllegalArgumentException e) {
            // e.printStackTrace(System.out);
            return true;
        }
    }

    /**
     * Metodo para verificar que las dimensiones para obtener cierto elemento
     * son menores que la longitud de las dimensiones de la declaracion del
     * arreglo
     *
     * @param length
     * @param indexes
     * @return
     */
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
