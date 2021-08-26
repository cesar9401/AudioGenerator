package com.cesar31.audiogenerator.main;

/**
 *
 * @author cesar31
 */
public class Test {

    public static void main(String[] args) {
        String note = "Do#";
        System.out.println(note.toLowerCase());
    }

    public static int getValue(int a) {
        if (a == 2) {
            return a;
        } else if (a == 3) {
            switch (a) {
                case 1:
                    return 1;
                default:
            }
        } else {
            return a;
        }

        for (int i = 0; i < 10; i++) {
            return a;
        }

        while (a == 2) {
            return a;
        }

        do {
            a--;
            if (a == 2) {
                return a;
            }
            
        } while (a > 0);

        return a;
    }
}
