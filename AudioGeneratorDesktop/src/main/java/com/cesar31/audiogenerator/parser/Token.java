package com.cesar31.audiogenerator.parser;

/**
 *
 * @author cesar31
 */
public class Token {

    private int type;
    private String value;
    private int line;
    private int column;

    public Token(int type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public Token(int type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value=" + value + ", line=" + line + ", column=" + column + '}';
    }
}
