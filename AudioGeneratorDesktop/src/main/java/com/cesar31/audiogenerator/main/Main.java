package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.instruction.Ins;
import com.cesar31.audiogenerator.instruction.Instruction;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.instruction.While;
import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        String input = FileControl.readData("input_files/input4.txt");
        System.out.println(input);
        System.out.println("");

        StringReader reader = new StringReader(input);
        AudioLex lex = new AudioLex(reader);

        AudioParser parser = new AudioParser(lex);
        try {
            List<Instruction> ast = (List<Instruction>) parser.parse().value;
            System.out.println("ast size -> " + ast.size());
            for(Instruction i : ast) {
                System.out.println(i.getClass().getSimpleName());
            }
            
            Stack<Ins> ins = parser.getStack();
            checkStack(ins);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        // test();
    }

    private static void checkStack(Stack<Ins> ins) {
        System.out.println("stack size -> " + ins.size());
        
        for (Ins i : ins) {
            System.out.println(i.getClass().getName());
            for (Instruction in : i.getInstructions()) {
                System.out.println("\t" + in.getClass().getName());
                if(in instanceof While) {
                    System.out.println(((While) in).getInstructions().size());
                }
            }
        }
    }

    public static void test() {
        int a = 12;
        int b = 5;
        int c = 10;

        //a = 10 + 2 * b++;
        //a = 13 - 2 / b++;
        c = a++ * b++;

        System.out.println(c);
        System.out.println(a);
        System.out.println(b);
        System.out.println(a);
    }

    public static void listTest() {
        List<Variable> list = new ArrayList<>();
        LinkedList<Variable> list2 = new LinkedList<>();
    }

}
