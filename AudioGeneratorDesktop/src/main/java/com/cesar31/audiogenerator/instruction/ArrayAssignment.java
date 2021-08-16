package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.parser.Token;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    // solo declaracion
    public ArrayAssignment(Integer tab, boolean keep, Token type, Token id, List<Operation> dimensions, Object value) {
        this.tab = tab;
        this.keep = keep;
        this.type = type;
        this.id = id;
        this.dimensions = dimensions;
        this.value = value;
    }

    @Override
    public Object run(SymbolTable table) {
        int[] dimension = new int[dimensions.size()];

        for (int i = 0; i < dimensions.size(); i++) {
            Variable v = dimensions.get(i).run(table);
            if (v.getType() == Var.INTEGER) {
                dimension[i] = Integer.valueOf(v.getValue());
            } else {
                // Agregar error aqui
                System.out.println("Se necesita variable de tipo entero para definir dimensiones de arreglos");
            }
        }
            
        System.out.println(Arrays.toString(dimension));
        
        if (value != null) {
            int[] dim = new int[dimension.length];
            for (int i = dim.length - 1; i >= 0; i--) {
                for (int j = 0; j < dimension[i]; j++) {
                    dim[i] = j;
//                    System.out.println(Arrays.toString(dim));
                    getValue(table, dim);
                }
            }
        }

        return null;
    }

    public void getValue(SymbolTable table, int[] dim) {
        int i = 1;
        Object tmp = Array.get(value, dim[0]);
        while (i < dim.length) {
            tmp = Array.get(tmp, dim[i]);
            i++;
        }

//        System.out.println(tmp.toString());
        Operation aux = (Operation) tmp;
        Variable v = aux.run(table);
        System.out.println(Arrays.toString(dim) + " -> " + v.getValue());
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
