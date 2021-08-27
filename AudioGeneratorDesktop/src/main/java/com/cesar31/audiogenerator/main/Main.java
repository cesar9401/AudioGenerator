package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.control.ParserHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.*;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        String input = FileControl.readData("input_files/input2.txt");
        System.out.println(input);
        System.out.println("\n------------------------------------------------------------------------\n");

        ParserHandler parser = new ParserHandler();
        parser.parseSource(input);

        long t1 = System.currentTimeMillis() - t;
        System.out.println("\n------------------------------------------------------------------------");
        System.out.println("Tiempo de ejecucion: " + t1 + " ms");
    }   

    private static void run(List<Instruction> ast) {
        if (ast != null) {
            System.out.println("AST -> " + ast.size());
            SymbolTable table = new SymbolTable();
            OperationHandler handler = new OperationHandler();

            for (Instruction i : ast) {
                System.out.println("ast -> " + i.getClass().getSimpleName());
                i.run(table, handler);
            }

            if (!handler.getErrors().isEmpty()) {
                for (Err e : handler.getErrors()) {
                    System.out.println(e.toString());
                }
            }

        } else {
            System.out.println("ast null");
        }
    }

    private static void checkAst(List<Instruction> ast, boolean exit_, boolean continue_) {
        for (int a = 0; a < ast.size(); a++) {
            Instruction i = ast.get(a);
            // Metodo principal
            if (i instanceof Principal) {
                List<Instruction> tmp = ((Principal) i).getInstructions();
                checkAst(tmp, true, true);
            }

            // Todos los posibles if - else if - selse
            if (i instanceof IfInstruction) {
                for (If j : ((IfInstruction) i).getInstructions()) {
                    List<Instruction> tmp = j.getInstructions();
                    checkAst(tmp, true, true);
                }
            }

            // Todos los switch
            if (i instanceof Switch) {
                List<Case> cases = ((Switch) i).getInstructions();
                for (Case c : cases) {
                    // Revisar que no tengan continue
                    List<Instruction> tmp = c.getInstructions();

                    // Interesa ver que no contenga continue, exit es opcional
                    checkAst(tmp, false, true);
                }
            }

            if (i instanceof Continue && continue_) {
                System.out.println("Error: " + ((Continue) i).getToken());
            }

            if (i instanceof Exit && exit_) {
                System.out.println("Error: " + ((Exit) i).getToken());
            }
        }
    }

    // se verifican en ciclos y switch
    private static void checkExit(List<Instruction> ast) {
        for (int i = 0; i < ast.size(); i++) {
            Instruction ins = ast.get(i);

            // Metodo principal
            if (ins instanceof Principal) {
                checkExit(((Principal) ins).getInstructions());
            }

            // Instrucciones switch
            if (ins instanceof Switch) {
                List<Case> cases = ((Switch) ins).getInstructions();
                for (Case c : cases) {
                    boolean value = checkExitIn(c.getInstructions(), true);
                    System.out.println("Valor: " + value);
                }
            }

            // Instrucciones for
            if (ins instanceof For) {
                checkExitIn(((For) ins).getInstructions(), false);
            }

            // Instrucciones While
            if (ins instanceof While) {
                checkExitIn(((While) ins).getInstructions(), false);
            }

            // Instrucciones Do-While
            if (ins instanceof DoWhile) {
                checkExitIn(((DoWhile) ins).getInstructions(), false);
            }
        }
    }

    private static boolean checkExitIn(List<Instruction> ast, boolean ignoreContinue) {
        for (int i = 0; i < ast.size(); i++) {
            Instruction tmp = ast.get(i);

            // Instrucciones switch
            if (tmp instanceof Switch) {
                List<Case> cases = ((Switch) tmp).getInstructions();
                for (Case c : cases) {
                    checkExitIn(c.getInstructions(), true);
                }
            }

            // Instruccioens if
            if (tmp instanceof IfInstruction) {
                int size = ((IfInstruction) tmp).getInstructions().size();
                IfInstruction aux = (IfInstruction) tmp;

                boolean value = true && aux.getInstructions().get(size - 1).getType() == If.Type.ELSE;
                for (int j = 0; j < size; j++) {
                    List<Instruction> tmp2 = aux.getInstructions().get(j).getInstructions();
                    boolean val = checkExitIn(tmp2, ignoreContinue);
                    value = value && val;
                }

                if (value) {
                    for (int j = i + 1; j < ast.size(); j++) {
                        System.out.println("No se ejecuta: " + ast.get(j).getClass().getSimpleName() + " -> " + ast.get(j).getInfo());
                    }
                    return true;
                }
            }

            // instrucciones for
            if (tmp instanceof For) {
                checkExitIn(((For) tmp).getInstructions(), false);
            }

            // instrucciones while
            if (tmp instanceof While) {
                checkExitIn(((While) tmp).getInstructions(), false);
            }

            // instrucciones do-while
            if (tmp instanceof DoWhile) {
                checkExitIn(((DoWhile) tmp).getInstructions(), false);
            }

            if (tmp instanceof Exit) {
                for (int j = i + 1; j < ast.size(); j++) {
                    System.out.println("No se ejecutan: " + ast.get(j).getClass().getSimpleName() + " -> " + ast.get(j).getInfo());
                }
                return true;
            }

            if (tmp instanceof Continue && !ignoreContinue) {
                return false;
            }
        }
        return false;
    }
}
