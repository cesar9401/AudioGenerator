package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;

/**
 *
 * @author csart
 */
public class Test {
    public static void main(String[] args) {
        String path = "input_files/request/request2.txt";
        String input = FileControl.readData(path);
        System.out.println(input);
        
    }
}
