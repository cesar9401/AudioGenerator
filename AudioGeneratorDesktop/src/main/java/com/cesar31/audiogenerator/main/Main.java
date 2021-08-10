package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        String input = FileControl.readData("input_files/input2.txt");
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
    }
}
