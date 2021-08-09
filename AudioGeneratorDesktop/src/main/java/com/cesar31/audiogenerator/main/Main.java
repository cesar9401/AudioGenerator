package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.FileControl;
import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;
import java.lang.reflect.Array;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
//        String in = "var entero arreglo arr1, arr [2][3][2] = { { {1,2},{3,4},{5,6} }, { {7,8},{9,10},{11,12} } }";
//        String in2 = "var entero arreglo arr1, arr [2][3][2] = {1,2}";
//        String in3 = "var entero arreglo arr1, arr [2][3] = {{1,2,3},{1,2,3}}";
//        String in4 = "var entero arreglo arr1, arr [2][3] = { { {1},{2},{3} },{ {4},{5},{6} } }";
//        String in5 = "var entero arreglo arr1 [2][2][2][2] = { { { {12,2},{2,3} },{ {4,5},{1,2} } },{ { {3,4},{4,3} },{ {1,2},{2,4} } } }";
//        String in6 = "var entero arreglo arr0 [2][3][5] = { { { 1,2,3,4,5 }, { 2,1,3,4,5 }, { 1,4,3,2,5 } },{ { 5,4,3,2,1 }, { 3,4,2,5,1 }, { 1,5,4,2,3 } } }";
//        String in7 = "var entero arreglo arra1, aar3, aar3[2 + b][c + 1] = { { { 1,2,3,4,5 }, { 2,1,3,4,5 }, { 1,4,3,2,5 } },{ { 5,4,3,2,1 }, { 3,4,2,5,1 }, { 1,5,4,2,3 } } }";
//        String in8 = "arr[1][2] += 12 + arr[3][2 + id1 + arr[1][1]]";
//
//        String in9 = "si(true && 12 >= ab)"
//                + "sino";

//        String in10 = "para(i+=1; i <= 12 && arr[1] == 12; i++)";
        
        String input = FileControl.readData("input_files/input1.txt");
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
        // createArray();
    }

    public static void createArray() {
        int[] size = {4, 3, 5};
//        size[0]++;
//        size[0]--;
//        size[0] += 12;
//        System.out.println(size[0]);

        Class clazz = Integer.class;
        Object t = Array.newInstance(clazz, size);

        System.out.println(Array.getLength(t));

        Object u = Array.get(t, 0);
        System.out.println(Array.getLength(u));

        Object v = Array.get(u, 0);
        System.out.println(Array.getLength(v));

        Array.set(v, 0, 10);
        System.out.println(Array.get(v, 0));
    }

    public static void test() {
        int[] size = {4, 3, 5};
        int i = 0;
        for (size[0]+=1; i < 10; size[1] = 1) {
            System.out.println(size[0]);
            System.out.println(i);
        }
    }
}
