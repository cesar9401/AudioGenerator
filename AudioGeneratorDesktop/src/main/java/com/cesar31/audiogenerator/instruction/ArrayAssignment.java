package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.ArrayHandler;
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
public class ArrayAssignment implements Instruction {

    private Integer tab;
    private boolean keep;
    private Token type;
    private Token id;
    private List<Operation> dimensions;
    private Object value;

    // para evaluar dimensiones del arreglo
    List<Integer> ind;

    // solo declaracion
    public ArrayAssignment(Integer tab, boolean keep, Token type, Token id, List<Operation> dimensions, Object value, List<Integer> ind) {
        this.tab = tab;
        this.keep = keep;
        this.type = type;
        this.id = id;
        this.dimensions = dimensions;
        this.value = value;

        this.ind = ind;
    }

    @Override
    public Object run(SymbolTable table) {
        // ArrayHandler
        ArrayHandler handler = new ArrayHandler();
        int[] dimension = new int[dimensions.size()];
        boolean error = false;

        for (int i = 0; i < dimensions.size(); i++) {
            Variable v = dimensions.get(i).run(table);
            if (v.getType() == Var.INTEGER) {
                dimension[i] = Integer.valueOf(v.getValue());
            } else {
                // Agregar error aqui
                error = true;
                System.out.println("Se necesita variable de tipo entero para definir dimensiones de arreglos");
            }
        }

        // solo declaracion
        if (value == null) {
            if (!error) {
                // crear arreglo
                handler.addArrayStatementToSymbolTable(type, id, keep, dimension, table);
            }
        } else {
            if (!error) {
                handler.addArrayAssignmentTSymbolTable(type, id, keep, dimension, value, ind, table);
            }
        }

//        if (value != null) {
//            // Verificar errores, que las dimensiones esten completas y que todos los valores esten agregados
//            // y crear nuevas variables Variable para que los valores no cambien
//            int[] aux = new int[dimension.length];
//            HashMap<String, String> map = new HashMap<>();
//            List<int[]> indexes = new ArrayList<>();
//            travel(dimension.length - 1, 0, dimension, aux, map, indexes);
//
//            System.out.println("Elementos: " + indexes.size());
//            for (int[] i : indexes) {
//                Operation tmp = getValue(i);
//                Variable v = tmp.run(table);
//            }
//        }

        return null;
    }

    public void travel(int total, int current, int[] dimensions, int[] aux, HashMap<String, String> map, List<int[]> indexes) {
        for (int i = 0; i < dimensions[current]; i++) {
            aux[current] = i;

            if (current < total) {
                travel(total, current + 1, dimensions, aux, map, indexes);
            }

            String tmp = Arrays.toString(aux);
            if (!map.containsKey(tmp)) {
                map.put(tmp, tmp);

                int[] newArray = Arrays.copyOf(aux, aux.length);
                System.out.println(Arrays.toString(newArray));
                indexes.add(newArray);
            }
        }
    }

    private Operation getValue(int[] dimension) {
        int i = 1;
        Object tmp = Array.get(value, dimension[0]);
        while (i < dimension.length) {
            tmp = Array.get(tmp, dimension[i]);
            i++;
        }
        return (Operation) tmp;
    }

    @Override
    public Integer getTab() {
        return this.tab;
    }

    @Override
    public void setTab(Integer tab) {
        this.tab = tab;
    }

    @Override
    public void sayName() {
        System.out.println("Array-Assignment");
    }

    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public Token getType() {
        return type;
    }

    public void setType(Token type) {
        this.type = type;
    }

    public Token getId() {
        return id;
    }

    public void setId(Token id) {
        this.id = id;
    }

    public List<Operation> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Operation> dimensions) {
        this.dimensions = dimensions;
    }
}
