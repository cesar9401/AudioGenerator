package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.Track;
import com.cesar31.audiogenerator.ui.MainView;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author cesar31
 */
public class MainControl {

    private Track track;
    
    public void initWindow() {
        java.awt.EventQueue.invokeLater(() -> {
            MainView view = new MainView(this);
            view.setLocationRelativeTo(null);
            view.setVisible(true);
        });
    }
    
    /**
     * Enviar texto para parseo (Lenguaje principal)
     * @param input
     * @param log 
     */
    public List<Err> parseSource(String input, JTextArea log) {
        track = null;
        
        ParserHandler parser = new ParserHandler(log);
        List<Err> errors = parser.parseSource(input);
        
        if(errors.isEmpty()) {
            this.track = parser.getTrack();
        }
        
        return errors;
    }
    
    public void saveTrack(MainView view) {
        if(track != null) {
            System.out.println("Guardar pista aqui :v");
        }
    }

    public Track getTrack() {
        return track;
    }
}
