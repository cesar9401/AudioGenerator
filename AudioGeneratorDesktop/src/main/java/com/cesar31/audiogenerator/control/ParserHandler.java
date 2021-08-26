package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.*;
import com.cesar31.audiogenerator.parser.*;
import java.io.StringReader;
import java.util.ArrayList;
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

                // Revisar ast en busca de instrucciones salir y continuar donde no deben ir
                checkAst(ast, errors, true, true);

                // Revisar ast en busca de instrucciones que no se ejecutan por la instruccion salir
                checkExitInAst(ast, errors);

                // Revisar ast en busca de instrucciones return que hacen falta e instrucciones que no se ejecutan por tener un return antes
                // Ejecutar test para verificar errores semanticos
                if (!errors.isEmpty()) {
                    errors.forEach(System.out::println);
                } else {
                    // testear codigo
                    errors = testAst(ast);
                    if (!errors.isEmpty()) {
                        // Mostrar errors
                        errors.forEach(System.out::println);
                    } else {
                        // Si no hay errores, ejecutar codigo
                        errors = runAst(ast);
                        if (!errors.isEmpty()) {
                            System.out.println("Se entraron los siguientes errores");
                            errors.forEach(System.out::println);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Resivar ast en busca de instrucciones salir y continuar donde no van
     *
     * @param ast
     * @param exit_
     * @param continue_
     * @param errors
     */
    private void checkAst(List<Instruction> ast, List<Err> errors, boolean exit_, boolean continue_) {

        for (int a = 0; a < ast.size(); a++) {
            Instruction i = ast.get(a);
            // Metodo principal
            if (i instanceof Principal) {
                List<Instruction> tmp = ((Principal) i).getInstructions();
                checkAst(tmp, errors, true, true);
            }

            // Todos los posibles if - else if - selse
            if (i instanceof IfInstruction) {
                for (If j : ((IfInstruction) i).getInstructions()) {
                    List<Instruction> tmp = j.getInstructions();
                    checkAst(tmp, errors, exit_, continue_);
                }
            }

            // Todos los switch
            if (i instanceof Switch) {
                List<Case> cases = ((Switch) i).getInstructions();
                for (Case c : cases) {
                    List<Instruction> tmp = c.getInstructions();

                    // Interesa ver que no contenga continue, exit es opcional
                    checkAst(tmp, errors, false, true);
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
                    Token t = ast.get(i).getInfo();
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

    private List<Err> testAst(List<Instruction> ast) {
        SymbolTable table = new SymbolTable();
        OperationHandler handler = new OperationHandler();

        for (Instruction i : ast) {
            i.test(table, handler);
        }

        return handler.getErrors();
    }

    private List<Err> runAst(List<Instruction> ast) {
        SymbolTable table = new SymbolTable();
        OperationHandler handler = new OperationHandler();

        for (Instruction i : ast) {
            i.run(table, handler);
        }

        return handler.getErrors();
    }
}
