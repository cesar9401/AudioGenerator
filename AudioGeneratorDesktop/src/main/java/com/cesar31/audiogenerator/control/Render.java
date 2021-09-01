package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.instruction.Var;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.model.Sound;
import com.cesar31.audiogenerator.parser.Token;

/**
 *
 * @author cesar31
 */
public class Render {

    private OperationHandler handler;

    public Render(OperationHandler handler) {
        this.handler = handler;
    }

    public Variable createSound(Token info, Token tokenN, Variable eighth, Variable milli, Variable channel) {
        String n = getNote(tokenN);
        int oct = Integer.valueOf(eighth.getValue());
        double mil = Double.valueOf(milli.getValue()) / 1000d;
        int chann = Integer.valueOf(channel.getValue());

        if (oct < 0 || oct > 8) {
            // Error aqui
            return null;
        }

        if (mil < 0) {
            // Error aqui
            return null;
        }

        if (chann < 0) {
            // Error aqui
            return null;
        }

        Sound sound = new Sound(n, String.valueOf(oct), mil, chann);
        System.out.println(sound);

        return new Variable(Var.INTEGER, milli.getValue());
    }

    public Variable createWait(Token info, Variable milli, Variable channel) {

        double mil = Double.valueOf(milli.getValue()) / 1000d;
        int chann = Integer.valueOf(channel.getValue());

        if (mil < 0) {
            // Error aqui
            return null;
        }

        if (chann < 0) {
            // Error aqui
            return null;
        }

        Sound sound = new Sound("R", "", mil, chann);
        System.out.println(sound);

        return new Variable(Var.INTEGER, milli.getValue());
    }

    private String getNote(Token note) {
        String value = note.getValue().toLowerCase();

        switch (value) {
            case "do":
                return "C";
            case "do#":
                return "C#";
            case "re":
                return "D";
            case "re#":
                return "D#";
            case "mi":
                return "E";
            case "fa":
                return "F";
            case "fa#":
                return "F#";
            case "sol":
                return "G";
            case "sol#":
                return "G#";
            case "la":
                return "A";
            case "la#":
                return "A#";
            case "si":
                return "B";
        }
        return "";
    }

}
