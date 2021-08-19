package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Variable {

    private Var type;
    private String id;
    private String value;
    private Integer level;
    private boolean keep;

    private Token token;

    // variables para arreglos
    private int [] dimensions;
    private Object array;
    
    public Variable(Token token) {
        this.token = token;
    }

    public Variable(Var type, String value) {
        this.type = type;
        this.value = value;
    }

    public Variable(Var type, String id, String value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }
    
    // constructor para arreglos

    public Variable(Var type, String id, boolean keep, int[] dimensions, Object array) {
        this.type = type;
        this.id = id;
        this.keep = keep;
        this.dimensions = dimensions;
        this.array = array;
    }

    public Var getType() {
        return type;
    }

    public void setType(Var type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isKeep() {
        return keep;
    }

    public void setKeep(boolean keep) {
        this.keep = keep;
    }

    public int[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
    }

    public Object getArray() {
        return array;
    }

    public void setArray(Object array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "Variable{" + "type=" + type + ", id=" + id + ", value=" + value + ", keep=" + keep + '}';
    }
}
