package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.control.OperationHandler;
import com.cesar31.audiogenerator.instruction.*;
import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Test {

    public static void main(String[] args) {
        String input = FileControl.readData("input_files/input2.txt");
        System.out.println(input);

        AudioLex lex = new AudioLex(new StringReader(input));
        AudioParser parser = new AudioParser(lex);

        try {
            List<Instruction> ast = (List<Instruction>) parser.parse().value;
            if (ast != null) {
                System.out.println("ast: " + ast.size());
                run(ast);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void run(List<Instruction> ast) {
        SymbolTable table = new SymbolTable();
        OperationHandler handler = new OperationHandler();
        handler.setFather(table);
        
        for (Instruction i : ast) {
            if (i instanceof Function) {
                String id = ((Function) i).getFunctionId();
                handler.getFunctions().put(id, (Function) i);
            }
        }

        for (Instruction i : ast) {
            if (i instanceof Assignment || i instanceof ArrayStatement || i instanceof Principal) {
                i.run(table, handler);
            }
        }
    }
}
