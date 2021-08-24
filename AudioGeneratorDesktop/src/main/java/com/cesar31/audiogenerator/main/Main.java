package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.Continue;
import com.cesar31.audiogenerator.instruction.Exit;
import com.cesar31.audiogenerator.instruction.For;
import com.cesar31.audiogenerator.instruction.If;
import com.cesar31.audiogenerator.instruction.IfInstruction;
import com.cesar31.audiogenerator.instruction.Instruction;
import com.cesar31.audiogenerator.instruction.Principal;
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
        String input = FileControl.readData("input_files/input5.txt");
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
                //checkAst(ast);
                //run(ast);
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

    private static void checkAst(List<Instruction> ast) {
        for (Instruction i : ast) {
            if (i instanceof Principal) {
                List<Instruction> tmp = ((Principal) i).getInstructions();
                checkAst(tmp);
            }

            if (i instanceof IfInstruction) {
                for (If j : ((IfInstruction) i).getInstructions()) {
                    List<Instruction> tmp = j.getInstructions();
                    checkAst(tmp);
                }
            }

            if (i instanceof Continue) {
                System.out.println("Error: " + ((Continue) i).getToken());
            }

            if (i instanceof Exit) {
                System.out.println("Error: " + ((Exit) i).getToken());
            }
        }
    }
}
