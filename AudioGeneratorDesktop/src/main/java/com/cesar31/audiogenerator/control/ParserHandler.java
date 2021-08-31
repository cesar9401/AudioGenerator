package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.*;
import com.cesar31.audiogenerator.parser.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ParserHandler {

    public ParserHandler() {

    }

    public void parseSource(String data) {
        AudioLex lex = new AudioLex(new StringReader(data));
        AudioParser parser = new AudioParser(lex);

        try {
            List<Instruction> ast = (List<Instruction>) parser.parse().value;
            List<Err> errors = parser.getErrors();
            if (!errors.isEmpty()) {
                errors.forEach(System.out::println);
            } else {
                errors = new ArrayList<>();

                // Revisar ast en busca de funciones "repetidas"
                checkAstForRepeatedFunctions(ast, errors, new HashMap<>());

                // Revisar ast en busca de instrucciones retorna donde no deben ir
                // Revisar ast en busca de instrucciones salir y continuar donde no deben ir
                checkAst(ast, errors, true, true, true);

                // Revisar ast en busca de instrucciones que no se ejecutan por la instruccion salir
                checkExitInAst(ast, errors);

                // Revisar ast en busca de instrucciones return que hacen falta e instrucciones que no se ejecutan por tener un return antes
                checkReturnInAst(ast, errors);

                // Ejecutar test para verificar errores semanticos
                if (!errors.isEmpty()) {
                    errors.forEach(System.out::println);
                } else {
                    // testear codigo
                    System.out.println("Test");
                    errors = testAst(ast);
                    if (!errors.isEmpty()) {
                        // Mostrar errors
                        System.out.println("Errores test");
                        errors.forEach(System.out::println);
                    } else {
                        // Si no hay errores, ejecutar codigo
                        System.out.println("\nCodigo limpio!!\n");
                        errors = runAst(ast);
                        if (!errors.isEmpty()) {
                            System.out.println("\nSe encontraron los siguientes errores de ejecucion:\n");
                            errors.forEach(e -> {
                                e.setType(Err.TypeErr.EJECUCION);
                                System.out.println(e);
                            });
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void checkAstForRepeatedFunctions(List<Instruction> ast, List<Err> errors, HashMap<String, Instruction> map) {
        for (Instruction i : ast) {
            if (i instanceof Principal) {
                String id = ((Principal) i).getFunctionId();
                if (!map.containsKey(id)) {
                    map.put(id, i);
                } else {
                    // Error
                    Token t = i.getInfo();
                    Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), "principal");
                    String description = "Ya se ha establecido una funcion principal y no puede haber más de una.";
                    err.setDescription(description);
                    errors.add(err);
                }
            }

            if (i instanceof Function) {
                String id = ((Function) i).getFunctionId();
                if (!map.containsKey(id)) {
                    map.put(id, i);
                } else {
                    // Error
                    Token t = i.getInfo();
                    Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), ((Function) i).getId().getValue());
                    String description = "La funcion con la firma `" + id + "` ya esta definida.";
                    err.setDescription(description);
                    errors.add(err);
                }
            }
        }
    }

    /**
     * Resivar ast en busca de instrucciones salir, continuar y retorna donde no
     * van
     *
     * @param ast
     * @param exit_
     * @param continue_
     * @param errors
     */
    private void checkAst(List<Instruction> ast, List<Err> errors, boolean exit_, boolean continue_, boolean return_) {

        // No se revisan ciclos, debido a que ahi si son posibles intrucciones salir y continuar
        for (int a = 0; a < ast.size(); a++) {

            Instruction i = ast.get(a);
            // Metodo principal
            if (i instanceof Principal) {
                List<Instruction> tmp = ((Principal) i).getInstructions();
                checkAst(tmp, errors, true, true, true);
            }

            // Funciones
            if (i instanceof Function) {
                List<Instruction> tmp = ((Function) i).getInstructions();
                checkAst(tmp, errors, true, true, ((Function) i).getKind() == Var.VOID);
            }

            /* ciclos */
            if (i instanceof For) {
                checkAst(((For) i).getInstructions(), errors, false, false, return_);
            }

            if (i instanceof While) {
                checkAst(((While) i).getInstructions(), errors, false, false, return_);
            }

            if (i instanceof DoWhile) {
                checkAst(((DoWhile) i).getInstructions(), errors, false, false, return_);
            }
            /* ciclos */

            // Todos los posibles if - else if - selse
            if (i instanceof IfInstruction) {
                for (If j : ((IfInstruction) i).getInstructions()) {
                    List<Instruction> tmp = j.getInstructions();
                    checkAst(tmp, errors, exit_, continue_, return_);
                }
            }

            // Todos los switch
            if (i instanceof Switch) {
                List<Case> cases = ((Switch) i).getInstructions();
                for (Case c : cases) {
                    List<Instruction> tmp = c.getInstructions();

                    // Interesa ver que no contenga continue, exit es opcional
                    checkAst(tmp, errors, false, true, return_);
                }
            }

            if (i instanceof Continue && continue_) {
                Token t = i.getInfo();
                Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                String description = "La sentencia `" + t.getValue() + "` unicamente debe incluirse dentro de ciclos(`para`, `mientras`, `hacer-mientras`).";
                err.setDescription(description);
                errors.add(err);
            }

            if (i instanceof Exit && exit_) {
                Token t = i.getInfo();
                Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                String description = "La sentencia `" + t.getValue() + "` unicamente debe incluirse dentro de ciclos(`para`, `mientras`, `hacer-mientras`) y dentro de `switch`.";
                err.setDescription(description);
                errors.add(err);
            }

            if (i instanceof Return && return_) {
                Token t = i.getInfo();
                Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                String description = "La sentencia `" + t.getValue() + "` unicamente debe incluirse dentro funciones que tengan un tipo de retorno.";
                err.setDescription(description);
                errors.add(err);
            }
        }
    }

    /**
     * Revisar instrucciones en busca de ciclos para enviarlos a metodo que
     * verifica instrucciones que no se ejecutan por instruccion salir
     *
     * @param ast
     */
    private void checkExitInAst(List<Instruction> ast, List<Err> errors) {
        for (int i = 0; i < ast.size(); i++) {
            Instruction ins = ast.get(i);

            // Metodo principal
            if (ins instanceof Principal) {
                checkExitInAst(((Principal) ins).getInstructions(), errors);
            }

            // Funciones
            if (ins instanceof Function) {
                checkExitInAst(((Function) ins).getInstructions(), errors);
            }

            // Instrucciones switch
            if (ins instanceof Switch) {
                List<Case> cases = ((Switch) ins).getInstructions();
                for (Case c : cases) {
                    boolean value = checkExitInAst(c.getInstructions(), errors, true);
                }
            }

            // Instrucciones for
            if (ins instanceof For) {
                boolean value = checkExitInAst(((For) ins).getInstructions(), errors, false);
            }

            // Instrucciones While
            if (ins instanceof While) {
                boolean value = checkExitInAst(((While) ins).getInstructions(), errors, false);
            }

            // Instrucciones Do-While
            if (ins instanceof DoWhile) {
                boolean value = checkExitInAst(((DoWhile) ins).getInstructions(), errors, false);
            }
        }
    }

    /**
     * Verificar instrucciones que no se ejecutan por la instruccion salir
     *
     * @param ast
     * @param ignoreContinue
     * @return
     */
    private boolean checkExitInAst(List<Instruction> ast, List<Err> errors, boolean ignoreContinue) {
        for (int i = 0; i < ast.size(); i++) {
            Instruction tmp = ast.get(i);

            // Instrucciones switch
            if (tmp instanceof Switch) {
                List<Case> cases = ((Switch) tmp).getInstructions();
                for (Case c : cases) {
                    checkExitInAst(c.getInstructions(), errors, true);
                }
            }

            // Instruccioens if
            if (tmp instanceof IfInstruction) {
                int size = ((IfInstruction) tmp).getInstructions().size();
                IfInstruction aux = (IfInstruction) tmp;

                boolean value = true && aux.getInstructions().get(size - 1).getType() == If.Type.ELSE;
                for (int j = 0; j < size; j++) {
                    List<Instruction> tmp2 = aux.getInstructions().get(j).getInstructions();
                    boolean val = checkExitInAst(tmp2, errors, ignoreContinue);
                    value = value && val;
                }

                if (value) {
                    for (int j = i + 1; j < ast.size(); j++) {
                        Token t = ast.get(j).getInfo();
                        Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                        String description = "La instruccion `" + t.getValue() + "` en la linea: " + t.getLine() + ", columna: " + t.getColumn() + " no se puede ejecutar(Por tener instruccion/es salir antes de esta instruccion).";
                        err.setDescription(description);
                        errors.add(err);
                    }
                    return true;
                }
            }

            // instrucciones for
            if (tmp instanceof For) {
                checkExitInAst(((For) tmp).getInstructions(), errors, false);
            }

            // instrucciones while
            if (tmp instanceof While) {
                checkExitInAst(((While) tmp).getInstructions(), errors, false);
            }

            // instrucciones do-while
            if (tmp instanceof DoWhile) {
                checkExitInAst(((DoWhile) tmp).getInstructions(), errors, false);
            }

            if (tmp instanceof Exit) {
                for (int j = i + 1; j < ast.size(); j++) {
                    Token t = ast.get(j).getInfo();
                    Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                    String description = "Debido a la instruccion `" + tmp.getInfo().getValue() + "` en linea:" + tmp.getInfo().getLine() + ", columna: " + tmp.getInfo().getColumn() + ", la instruccion `" + t.getValue() + "` no se ejecuta.";
                    err.setDescription(description);
                    errors.add(err);
                }
                return true;
            }

            if (tmp instanceof Continue && !ignoreContinue) {
                return false;
            }
        }
        return false;
    }

    private void checkReturnInAst(List<Instruction> ast, List<Err> errors) {
        for (Instruction i : ast) {
            if (i instanceof Function) {
                if (((Function) i).getKind() != Var.VOID) {
                    List<Instruction> tmp = ((Function) i).getInstructions();
                    boolean val = checkReturnInAst(tmp, errors, true, true);
                    if (!val) {
                        Token t = i.getInfo();
                        Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), ((Function) i).getId().getValue());
                        String description = "En la funcion `" + ((Function) i).getId().getValue() + "`, definida en la linea " + t.getLine() + ", columna " + t.getColumn() + ", hace falta la declaracion de retorno de la funcion.";
                        err.setDescription(description);
                        errors.add(err);
                    }
                }
            }
        }
    }

    private boolean checkReturnInAst(List<Instruction> ast, List<Err> errors, boolean ignoreContinue, boolean ignoreExit) {
        for (int i = 0; i < ast.size(); i++) {
            Instruction tmp = ast.get(i);

            // Instrucciones if
            if (tmp instanceof IfInstruction) {
                int size = ((IfInstruction) tmp).getInstructions().size();
                IfInstruction aux = (IfInstruction) tmp;

                boolean value = true && aux.getInstructions().get(size - 1).getType() == If.Type.ELSE;
                for (int j = 0; j < size; j++) {
                    List<Instruction> tmp2 = aux.getInstructions().get(j).getInstructions();
                    boolean val = checkReturnInAst(tmp2, errors, ignoreContinue, ignoreExit);
                    value = value && val;
                }

                if (value) {
                    for (int j = i + 1; j < ast.size(); j++) {
                        Token t = ast.get(j).getInfo();
                        Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                        String description = "La instruccion `" + t.getValue() + "` en la linea: " + t.getLine() + ", columna: " + t.getColumn() + " no se puede ejecutar(Por tener instruccion/es retorna antes de esta instruccion).";
                        err.setDescription(description);
                        errors.add(err);
                    }
                    return true;
                }
            }

            // Instruccion DoWhile
            if (tmp instanceof DoWhile) {
                List<Instruction> tmp2 = ((DoWhile) tmp).getInstructions();
                boolean value = checkReturnInAst(tmp2, errors, false, false);
                if (value) {
                    for (int j = i + 1; j < ast.size(); j++) {
                        Token t = ast.get(j).getInfo();
                        Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                        String description = "La instruccion `" + t.getValue() + "` en la linea: " + t.getLine() + ", columna: " + t.getColumn() + " no se puede ejecutar(Por tener instruccion/es retorna antes de esta instruccion).";
                        err.setDescription(description);
                        errors.add(err);
                    }
                    return true;
                }
            }

            // Instrucciones for
            if (tmp instanceof For) {
                checkReturnInAst(((For) tmp).getInstructions(), errors, false, false);
            }

            // Instrucciones while
            if (tmp instanceof While) {
                checkReturnInAst(((While) tmp).getInstructions(), errors, false, false);
            }

            // Instrucciones switch
            if (tmp instanceof Switch) {
                List<Case> cases = ((Switch) tmp).getInstructions();
                for (Case c : cases) {
                    checkReturnInAst(c.getInstructions(), errors, true, false);
                }
            }

            // instruccion retorna
            if (tmp instanceof Return) {
                for (int j = i + 1; j < ast.size(); j++) {
                    Token t = ast.get(j).getInfo();
                    Err err = new Err(Err.TypeErr.SINTACTICO, t.getLine(), t.getColumn(), t.getValue());
                    String description = "Debido a la instruccion `" + tmp.getInfo().getValue() + "` en linea:" + tmp.getInfo().getLine() + ", columna: " + tmp.getInfo().getColumn() + ", la instruccion `" + t.getValue() + "` no se ejecuta.";
                    err.setDescription(description);
                    errors.add(err);
                }
                return true;
            }

            // instruccion continuar
            if (tmp instanceof Continue && !ignoreContinue) {
                return false;
            }

            if (tmp instanceof Exit && !ignoreExit) {
                return false;
            }
        }

        return false;
    }

    private List<Err> testAst(List<Instruction> ast) {
        SymbolTable table = new SymbolTable();
        OperationHandler handler = new OperationHandler();
        handler.setFather(table);

        // Obtener funciones
        for (Instruction i : ast) {
            if (i instanceof Function) {
                String id = ((Function) i).getFunctionId();
                handler.getFunctions().put(id, (Function) i);
            }
        }

        // Test de declaracion de variables y arreglos y metodo principal
        List<Instruction> tmp = new ArrayList<>();
        for (Instruction i : ast) {
            if (i instanceof Assignment || i instanceof ArrayStatement || i instanceof Principal) {
                i.test(table, handler);
            } else {
                tmp.add(i);
            }
        }

        // Test de funciones
        handler.setTest(true);
        for (Instruction i : tmp) {
            i.test(table, handler);
        }

        return handler.getErrors();
    }

    private List<Err> runAst(List<Instruction> ast) {
        SymbolTable table = new SymbolTable();
        OperationHandler handler = new OperationHandler();
        handler.setFather(table);

        // Obtener funciones
        for (Instruction i : ast) {
            if (i instanceof Function) {
                String id = ((Function) i).getFunctionId();
                handler.getFunctions().put(id, (Function) i);
            }
        }

        // Ejecutar declaracion de variables, de arreglos y metodo principal
        for (Instruction i : ast) {
            if (i instanceof Assignment || i instanceof ArrayStatement || i instanceof Principal) {
                i.run(table, handler);
            } else {
                //System.out.println(i.getClass().getSimpleName());
            }
        }
        return handler.getErrors();
    }

}
