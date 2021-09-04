package com.cesar31.audiogenerator.instruction;

import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.parser.Token;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author cesar31
 */
public class Message implements Instruction, Serializable {

    private Token info;
    private Operation operation;

    public Message(Token info, Operation operation) {
        this.info = info;
        this.operation = operation;
    }

    @Override
    public Object run(SymbolTable table, OperationHandler handler) {
        Variable v = operation.run(table, handler);
        if (v != null) {
            if (v.getValue() != null) {
                String message = v.getValue() + "\n";
                handler.getLog().append(message);
                System.out.println(v.getValue());
            } else {
                // variable no tiene valor definido, no es posible emitir mensaje
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn());
                String description = "No es posible emitir el mensaje, esto puede ser debido a que uno de los operandos que se han pasado como parametros no tiene un valor definido.";
                if (v.getDimensions() != null) {
                    description = "No es posible emitir el mensaje, debido a que el arreglo `" + v.getId() + "` en la direccion `" + Arrays.toString(v.getDimensions()) + "` no tiene valor definido";
                }
                err.setDescription(description);
                handler.getErrors().add(err);
            }
        } else {
            // No es posible emitir mensaje
            Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), "");
            String description = "No es posible emitir el mensaje, esto puede ser debido a que uno de los operandos que se han pasado como parametros no tiene un valor definido o no ha sido declarado";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }

    @Override
    public Token getInfo() {
        return this.info;
    }

    @Override
    public Object test(SymbolTable table, OperationHandler handler) {
        Variable v = operation.test(table, handler);
        if (v != null) {
            if (v.getArray() != null) {
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), "");
                String description = "No es posible emitir mensaje, no se aceptan parametros de tipo `arreglo`";
                err.setDescription(description);
                handler.getErrors().add(err);

            } else if (v.getValue() == null) {
                // Variable no tiene valor definido
                Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), "");
                String description = "No es posible emitir el mensaje, esto puede ser debido a que uno de los operandos que se han pasado como parametros no tiene un valor definido.";

                if (v.getToken() != null) {
                    err.setLine(v.getToken().getLine());
                    err.setColumn(v.getToken().getColumn());
                    err.setLexema(v.getToken().getValue());
                    description = "No es posible emitir mensaje, esto debido a que el parametro `" + v.getToken().getValue() + "`, no tiene un valor definido.";
                }

                err.setDescription(description);
                handler.getErrors().add(err);
            }
        } else {
            // No es posible emitir mensaje
            Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), "");
            String description = "No es posible emitir el mensaje, esto puede ser debido a que uno de los operandos que se han pasado como parametros no tiene un valor definido o no ha sido declarado";
            err.setDescription(description);
            handler.getErrors().add(err);
        }

        return null;
    }
}
