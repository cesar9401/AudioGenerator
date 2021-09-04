package com.cesar31.audiogenerator.control;

import com.cesar31.audiogenerator.error.Err;
import com.cesar31.audiogenerator.instruction.Var;
import com.cesar31.audiogenerator.instruction.Variable;
import com.cesar31.audiogenerator.model.Sound;
import com.cesar31.audiogenerator.parser.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Render {

    private OperationHandler handler;
    private List<Sound> sounds;

    public Render(OperationHandler handler) {
        this.handler = handler;
        this.sounds = new ArrayList<>();
    }

    public double getDuration(List<Sound> tmp) {
        HashMap<Integer, Double> map = new HashMap<>();
        double max = 0;
        for (Sound s : tmp) {
            double duration = s.getMilliseconds();
            if (!map.containsKey(s.getChannel())) {
                map.put(s.getChannel(), duration);
            } else {
                duration += map.get(s.getChannel());
                map.put(s.getChannel(), duration);
            }

            if (duration > max) {
                max = duration;
            }
        }

        return max;
    }

    /**
     * Crear nueva nota para JFugue
     *
     * @param info
     * @param tokenN
     * @param eighth
     * @param milli
     * @param channel
     * @return
     */
    public Variable createSound(Token info, Token tokenN, Variable eighth, Variable milli, Variable channel) {
        String n = getNote(tokenN);
        int oct = Integer.valueOf(eighth.getValue());
        double mil = Double.valueOf(milli.getValue());
        int chann = Integer.valueOf(channel.getValue());

        if (oct < 0 || oct > 8) {
            Err err = new Err(Err.TypeErr.SINTACTICO, info.getLine(), info.getColumn(), "");
            String description = "En la llamada a la función reproducir, el segundo parametro(octava) no esta entre los limites permitidos(entre 0 y 8), no se puede proceder con la operacion.";
            err.setDescription(description);
            handler.getErrors().add(err);
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
        this.sounds.add(sound);

        return new Variable(Var.INTEGER, milli.getValue());
    }

    /**
     * Crear espera para JFugue
     *
     * @param info
     * @param milli
     * @param channel
     * @return
     */
    public Variable createWait(Token info, Variable milli, Variable channel) {

        double mil = Double.valueOf(milli.getValue());
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
        this.sounds.add(sound);

        return new Variable(Var.INTEGER, milli.getValue());
    }

    /**
     * Obtener nota para JFugue
     *
     * @param note
     * @return
     */
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

    public List<Sound> getSounds() {
        return sounds;
    }
}
