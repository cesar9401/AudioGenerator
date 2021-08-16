package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.instruction.Ins;
import com.cesar31.audiogenerator.instruction.Instruction;
import com.cesar31.audiogenerator.instruction.SymbolTable;
import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Stack;

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
            run(ast);

            Stack<Ins> ins = parser.getStack();
            System.out.println("stack size -> " + ins.size());
            System.out.println("");
            //checkStack(ins);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        long t1 = System.currentTimeMillis() - t;
        System.out.println(t1);
    }

    private static void run(List<Instruction> ast) {
        if (ast != null) {
            System.out.println("AST -> " + ast.size());
            SymbolTable table = new SymbolTable();
            for (Instruction i : ast) {
                System.out.println("ast -> " + i.getClass().getSimpleName());
                i.run(table);
            }
        } else {
            System.out.println("ast null");
        }
    }

    private static void checkStack(Stack<Ins> ins) {
        // System.out.println("stack size -> " + ins.size());

        for (Ins i : ins) {
            System.out.println(i.getClass().getSimpleName());
        }
    }
}
