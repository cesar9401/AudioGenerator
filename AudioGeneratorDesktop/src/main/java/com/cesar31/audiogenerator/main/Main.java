package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.Case;
import com.cesar31.audiogenerator.instruction.Continue;
import com.cesar31.audiogenerator.instruction.Exit;
import com.cesar31.audiogenerator.instruction.For;
import com.cesar31.audiogenerator.instruction.If;
import com.cesar31.audiogenerator.instruction.IfInstruction;
import com.cesar31.audiogenerator.instruction.Instruction;
import com.cesar31.audiogenerator.instruction.Principal;
import com.cesar31.audiogenerator.instruction.Switch;
import com.cesar31.audiogenerator.instruction.SymbolTable;
import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        String input = FileControl.readData("input_files/input3.txt");
        System.out.println(input);
        System.out.println("");

        StringReader reader = new StringReader(input);
        AudioLex lex = new AudioLex(reader);

        AudioParser parser = new AudioParser(lex);
        try {
            List<Instruction> ast = (List<Instruction>) parser.parse().value;
            List<Err> errors = parser.getErrors();
            if (!errors.isEmpty()) {
                for (Err e : errors) {
                    System.out.println(e.toString());
                }
            } else {
                // checkAst(ast, true, true);

                // Ejecutar solo si checkAst es correcto
                checkExit(ast);
                run(ast);
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        long t1 = System.currentTimeMillis() - t;
        System.out.println();
        System.out.println(t1);
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

    private static void checkExit(List<Instruction> ast) {
        for (int i = 0; i < ast.size(); i++) {
            Instruction ins = ast.get(i);

            if (ins instanceof Principal) {
                checkExit(((Principal) ins).getInstructions());
            }

            if (ins instanceof Switch) {
                List<Case> cases = ((Switch) ins).getInstructions();
                for (Case c : cases) {
                    boolean value = checkExitIn(c.getInstructions(), true);
                    System.out.println("Valor: " + value);
                }
            }

            if (ins instanceof For) {
                boolean value = checkExitIn(((For) ins).getInstructions(), false);
                System.out.println(value);
            }
        }
    }

    private static boolean checkExitIn(List<Instruction> ast, boolean ignoreContinue) {
        for (int i = 0; i < ast.size(); i++) {
            Instruction tmp = ast.get(i);

            if (tmp instanceof Switch) {
                List<Case> cases = ((Switch) tmp).getInstructions();
                for (Case c : cases) {
                    checkExitIn(c.getInstructions(), true);
                }
            }

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

            if (tmp instanceof Exit) {
                for (int j = i + 1; j < ast.size(); j++) {
                    System.out.println("No se ejecutan: " + ast.get(j).getClass().getSimpleName() + " -> " + ast.get(j).getInfo());
                }
                return true;
            }

            if (tmp instanceof For) {
                checkExitIn(((For) tmp).getInstructions(), false);
            }

            if (tmp instanceof Continue && !ignoreContinue) {
                return false;
            }
        }
        return false;
    }
}
