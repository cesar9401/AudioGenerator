package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.Function;
import com.cesar31.audiogenerator.instruction.SymbolTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class OperationHandler {

    private boolean test;

    private SymbolTable father;
    private HashMap<String, Function> functions;

    private ArrayHandler array;
    private CastHandler cast;
    private EnvironmentHandler environment;
    private OperationMaker operation;
    private NativeFunctions nativeF;
    
    private Render render;
    
    private List<Err> errors;

    public OperationHandler() {
        this.test = false;
        this.functions = new HashMap<>();
        this.array = new ArrayHandler(this);
        this.cast = new CastHandler(this);
        this.environment = new EnvironmentHandler(this);
        this.operation = new OperationMaker(this);
        this.nativeF = new NativeFunctions(this);
        
        this.render = new Render(this);
        
        this.errors = new ArrayList<>();
    }

    public ArrayHandler getArray() {
        return array;
    }

    public CastHandler getCast() {
        return cast;
    }

    public EnvironmentHandler getEnvironment() {
        return environment;
    }

    public OperationMaker getOperation() {
        return operation;
    }

    public List<Err> getErrors() {
        return errors;
    }

    public HashMap<String, Function> getFunctions() {
        return functions;
    }

    public NativeFunctions getNativeF() {
        return nativeF;
    }

    public void setFather(SymbolTable father) {
        this.father = father;
    }

    public SymbolTable getFather() {
        return father;
    }

    public boolean isTest() {
        return test;
    }

    public Render getRender() {
        return render;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
