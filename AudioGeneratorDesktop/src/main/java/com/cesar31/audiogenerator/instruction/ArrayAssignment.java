package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.lang.reflect.Array;
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
    public Object run(SymbolTable table, OperationHandler handler) {
        // ArrayHandler
        int[] dimension = new int[dimensions.size()];
        boolean error = false;

        for (int i = 0; i < dimensions.size(); i++) {
            Variable v = dimensions.get(i).run(table, handler);
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
                handler.getArray().addArrayStatementToSymbolTable(type, id, keep, dimension, table);
            }
        } else {
            if (!error) {
                // Declaracion y asignacionH
                handler.getArray().addArrayAssignmentToSymbolTable(type, id, keep, dimension, value, ind, table, handler);
            }
        }

        return null;
    }

    @Override
    public Integer getTab() {
        return this.tab;
    }

    @Override
    public void setTab(Integer tab) {
        this.tab = tab;
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
