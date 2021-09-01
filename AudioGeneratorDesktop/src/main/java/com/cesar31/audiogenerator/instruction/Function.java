package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.parser.Token;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Function implements Instruction {

    protected Token info;
    private Token id;
    private Token type;

    private Var kind;
    private boolean keep;

    private List<Parameter> params;
    private List<Instruction> instructions;

    // Valores de los parametros
    protected List<Variable> values;

    public Function() {
    }

    /**
     * Constructor funcion de tipo void
     *
     * @param info
     * @param keep
     * @param id
     * @param params
     * @param instructions
     */
    public Function(Token info, boolean keep, Token id, List<Parameter> params, List<Instruction> instructions) {
        this.info = info;

        this.keep = keep;
        this.id = id;
        this.params = params;
        this.instructions = instructions;

        this.kind = Var.VOID;
    }

    /**
     * Constructor para funciones que no son de tipo void(Tienen retorno)
     *
     * @param info
     * @param keep
     * @param type
     * @param id
     * @param params
     * @param instructions
     */
    public Function(Token info, boolean keep, Token type, Token id, List<Parameter> params, List<Instruction> instructions) {
        this.info = info;
        this.keep = keep;
        this.type = type;
        this.id = id;
        this.params = params;
        this.instructions = instructions;

        String value = type.getValue().toLowerCase();
        switch (value) {
            case "cadena":
                this.kind = Var.STRING;
                break;
            case "doble":
                this.kind = Var.DOUBLE;
                break;
            case "entero":
                this.kind = Var.INTEGER;
                break;
            case "caracter":
                this.kind = Var.CHAR;
                break;
            case "boolean":
                this.kind = Var.BOOLEAN;
                break;
        }
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        SymbolTable local = new SymbolTable(handler.getFather());

        // Se asignan las variables
        for (int i = 0; i < params.size(); i++) {
            params.get(i).setValue(values.get(i));
            params.get(i).run(local, handler);
        }

        for (Instruction i : this.instructions) {
            Object o = i.run(local, handler);
            if (o != null) {
                if (o instanceof Return) {
                    Operation tmp = ((Return) o).getOperation();
                    Variable v = tmp.run(local, handler);
                    return v;
                }
            }
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        SymbolTable local = new SymbolTable(handler.getFather());

        for (int i = 0; i < params.size(); i++) {
            params.get(i).setValue(new Variable(handler.getCast().getType(params.get(i).getType()), ""));
            params.get(i).test(local, handler);
        }

        for (Instruction i : this.instructions) {
            Object o = i.test(local, handler);
            if (o != null) {
                if (o instanceof Return) {
                    Operation tmp = ((Return) o).getOperation();
                    Variable v = tmp.test(local, handler);
                    return v;
                }
            }
        }

        return null;
    }

    @Override
    public Token getInfo() {
        return this.info;
    }

    public String getFunctionId() {
        String functionId = this.id.getValue() + "(";
        for (int i = 0; i < this.params.size(); i++) {
            functionId += params.get(i).getType().getValue().toLowerCase();
            if (i != params.size() - 1) {
                functionId += ",";
            }
        }
        functionId += ")";

        return functionId;
    }

    public Token getId() {
        return id;
    }

    public Var getKind() {
        return kind;
    }

    public Token getType() {
        return type;
    }

    public boolean isKeep() {
        return keep;
    }

    public List<Parameter> getParams() {
        return params;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public List<Variable> getValues() {
        return values;
    }

    public void setValues(List<Variable> values) {
        this.values = values;
    }
}
