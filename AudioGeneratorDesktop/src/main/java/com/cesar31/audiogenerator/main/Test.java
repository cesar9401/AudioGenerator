/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.instruction.Operation;
import com.cesar31.audiogenerator.instruction.OperationType;
import com.cesar31.audiogenerator.instruction.Var;
import com.cesar31.audiogenerator.instruction.Variable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Test {

    public static void main(String[] args) {
//        int[] dim = {4, 5, 3};
//
//        Object arreglo = Array.newInstance(String.class, dim);
//        //System.out.println(Array.getLength(arreglo));
//        
//        setValue(arreglo, "hello there!");
//        getValue(arreglo);
        //test1();
        int[] dimensions = {3, 3, 3};
        int[] dimensions2 = {0, 0, 0};
        HashMap<Integer, int[]> map = new HashMap<>();
        travel(3, 0, dimensions, dimensions2, map);
    }

    private static void travel(int total, int current, int[] dimensions, int[] dim2, HashMap<Integer, int[]> map) {
        if (current < total) {
            for (int i = 0; i < dimensions[current]; i++) {
                dim2[current] = i;
                travel(total, current + 1, dimensions, dim2, map);

                int n = getN(dim2);
                if (!map.containsKey(n)) {
                    System.out.println(Arrays.toString(dim2));
                    map.put(n, dim2);
                }
            }
        }
    }

    private static int getN(int[] dim) {
        int num = 0;
        for (int i = 0; i < dim.length; i++) {
            num *= 10;
            num += dim[i];
        }

        return num;
    }

    private static void setValue(Object array, String value) {
        int[] dim2 = {0, 1, 2};

        int i = 1;
        Object tmp = Array.get(array, dim2[0]);
        while (i < dim2.length - 1) {
            tmp = Array.get(tmp, dim2[i]);
            i++;
        }
        //System.out.println(Array.getLength(tmp));
        Array.set(tmp, dim2[i], value);
    }

    private static String getValue(Object arreglo) {
        int[] dim = {0, 1, 2};

        Object tmp = Array.get(arreglo, dim[0]);
        int i = 1;
        while (i < dim.length) {
            tmp = Array.get(tmp, dim[i]);
            i++;
        }
        //String value = (String) Array.get(tmp, dim[i]);
        //System.out.println(value);
        System.out.println(tmp.toString());

        return null;
    }

    private static void test1() {
        List<Object> list = new ArrayList<>();

        int[] dim = {3, 6};
        Object array = Array.newInstance(Operation.class, dim);
        int i = 1;
        Object tmp = Array.get(array, 0);
        System.out.println(Array.getLength(tmp));

        Array.set(tmp, 0, new Operation(OperationType.INTEGER, new Variable(Var.INTEGER, "10")));

        tmp = Array.get(tmp, 0);
        System.out.println(tmp);
        if (tmp instanceof Operation) {
            System.out.println("si");
            System.out.println(((Operation) tmp).getValue().toString());
        }

    }
}
