package com.cesar31.audiogenerator.error;

/**
 *
 * @author cesar31
 */
public class Err {

    public enum TypeErr {
        LEXICO,
        SINTACTICO,
        SEMANTICO,
        EJECUCION
    }

    private TypeErr type;
    private int line;
    private int column;
    private String lexema;
    private String description;

    public Err(TypeErr type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public Err(TypeErr type, int line, int column, String lexema) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.lexema = lexema;
    }

    public Err(TypeErr type, int line, int column, String lexema, String description) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.lexema = lexema;
        this.description = description;
    }

    public TypeErr getType() {
        return type;
    }

    public void setType(TypeErr type) {
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Err{" + "type=" + type + ", line=" + line + ", column=" + column + ", lexema=" + lexema + ", description=" + description + '}';
    }
}
