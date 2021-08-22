package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ArrayAssignment implements Instruction {

    private boolean keep;
    private Token type;
    private Token id;
    private List<ArrayIndex> dimensions;
    private Object value;

    // para evaluar dimensiones del arreglo
    List<Integer> ind;

    // solo declaracion
    public ArrayAssignment(boolean keep, Token type, Token id, List<ArrayIndex> dimensions, Object value, List<Integer> ind) {
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
            Variable v = dimensions.get(i).getOperation().run(table, handler);
            if (v != null) {
                if (v.getType() == Var.INTEGER) {
                    dimension[i] = Integer.valueOf(v.getValue());
                    if (dimension[i] <= 0) {
                        // Error, las dimensiones deben ser mayor a cero
                        error = true;
                        Token left = dimensions.get(i).getLbracket();

                        Err err = new Err(Err.TypeErr.SINTACTICO, left.getLine(), left.getColumn() + 1, "");
                        String description = "En la declaracion del arreglo " + id.getValue() + ", el indice numero " + (i + 1);
                        if (v.getId() != null) {
                            description += " con id: `" + v.getId() + "`";
                            err.setLexema(v.getId());
                            if (v.getToken() != null) {
                                err.setLine(v.getToken().getLine());
                                err.setColumn(v.getToken().getColumn());
                            }
                        }
                        description += ", con valor entero(value = `" + v.getValue() + "` no es valido para definir la longitud/dimension de un arreglo. Este debe ser mayor que cero.";
                        err.setDescription(description);
                        handler.getErrors().add(err);
                    }

                } else {
                    // Agregar error aqui
                    error = true;
                    Token left = dimensions.get(i).getLbracket();

                    Err err = new Err(Err.TypeErr.SINTACTICO, left.getLine(), left.getColumn() + 1, "");
                    String description = "En la declaracion del arreglo " + id.getValue() + ", el indice numero " + (i + 1);
                    if (v.getId() != null) {
                        description += " con id: `" + v.getId() + "`";
                        err.setLexema(v.getId());
                        if (v.getToken() != null) {
                            err.setLine(v.getToken().getLine());
                            err.setColumn(v.getToken().getLine());
                        }
                    }
                    description += ", no es de tipo entero(value = `" + v.getValue() + "`, tipo = `" + v.getType().getName() + "`). Se necesita una variable de tipo entero para definir la longitud/dimension de un arreglo.";
                    err.setDescription(description);
                    handler.getErrors().add(err);
                }
            } else {
                // Error variable no existe, error se determina en la clase operacion
                error = true;
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
                handler.getArray().addArrayAssignmentToSymbolTable(type, id, keep, dimension, value, ind, table);
            }
        }

        return null;
    }

    @Override
    public Integer getTab() {
        return 0;
    }

    @Override
    public void setTab(Integer tab) {
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

    public List<ArrayIndex> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<ArrayIndex> dimensions) {
        this.dimensions = dimensions;
    }
}
