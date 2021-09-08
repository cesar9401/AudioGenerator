package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.control.RequestParserHandler;

/**
 *
 * @author csart
 */
public class Test {

    public static void main(String[] args) {
        String path = "input_files/request/request2.txt";
        String input = FileControl.readData(path);
        System.out.println(input);
        
        RequestParserHandler handler = new RequestParserHandler();
        String res = handler.parseSource(input);
        System.out.println(res);
    }
}
