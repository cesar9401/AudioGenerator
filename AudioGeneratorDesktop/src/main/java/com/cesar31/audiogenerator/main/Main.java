package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        String input = FileControl.readData("input_files/input3.txt");
        System.out.println(input);
        System.out.println("");
        
        StringReader reader = new StringReader(input);
        AudioLex lex = new AudioLex(reader);

        AudioParser parser = new AudioParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        // test();
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
