package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class OperationHandler {

    private ArrayHandler array;
    private CastHandler cast;
    private EnvironmentHandler environment;
    private OperationMaker operation;
    private List<Err> errors;

    public OperationHandler() {
        this.array = new ArrayHandler();
        this.cast = new CastHandler();
        this.environment = new EnvironmentHandler();
        this.operation = new OperationMaker();
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
}
