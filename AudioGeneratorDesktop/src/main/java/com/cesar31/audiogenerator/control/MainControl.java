package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.ui.MainView;
import javax.swing.JTextArea;

/**
 *
 * @author cesar31
 */
public class MainControl {

    public void initWindow() {
        java.awt.EventQueue.invokeLater(() -> {
            MainView view = new MainView(this);
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }
    
    public void parseSource(String input, JTextArea log) {
        long t = System.currentTimeMillis();
        
        ParserHandler parser = new ParserHandler(log);
        parser.parseSource(input);
    }
}
