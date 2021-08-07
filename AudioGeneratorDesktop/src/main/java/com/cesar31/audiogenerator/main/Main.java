package com.cesar31.audiogenerator.main;

import com.cesar31.audiogenerator.parser.AudioLex;
import com.cesar31.audiogenerator.parser.AudioParser;
import java.io.StringReader;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        String in = "\"#\"Ca\tdena\n\t\r#nde## #tpru\neba\"";
        StringReader reader = new StringReader(in);
        AudioLex lex = new AudioLex(reader);

        AudioParser parser = new AudioParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
}
