package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.control.MainControl;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        MainControl control = new MainControl();
        control.initWindow();
        control.initListener();
    }
}
