package com.cesar31.audiogenerator.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author cesar31
 */
public class FileControl {

    public FileControl() {
    }

    /**
     * Leer archivo de texto
     *
     * @param path direccion donde esta el archivo
     * @return
     */
    public static String readData(String path) {
        String string = "";
        File file = new File(path);
        try {
            try ( BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null) {
                    string += line;
                    line = br.readLine();

                    if (line != null) {
                        string += "\n";
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return string;
    }

    /**
     * Imprimir o guarar archivo segun path y texto
     *
     * @param file direccion del archivo
     * @param txt texto a guardar
     */
    public void writeFile(File file, String txt) {
        try {
            try ( PrintWriter writer = new PrintWriter(file)) {
                writer.write(txt);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /**
     * Obtener informacion de linea y columna
     *
     * @param component
     * @return
     */
    public String lineAndColumnInfo(JTextArea component) {
        String info = "";
        int linea = 1;
        int columna = 1;

        int caretPos = component.getCaretPosition();
        try {
            linea = component.getLineOfOffset(caretPos);
            columna = caretPos - component.getLineStartOffset(linea);
            linea++;
        } catch (BadLocationException ex) {
            ex.printStackTrace(System.out);
        }

        info = "Linea: " + linea + ", Columna: " + columna;
        return info;
    }
}
