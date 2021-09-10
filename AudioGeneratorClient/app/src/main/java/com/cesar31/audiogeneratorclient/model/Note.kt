package com.cesar31.audiogeneratorclient.model

import java.io.Serializable

class Note(var name: String, var eighth: String, var duration: Double): Serializable {
    var channel = 0
    override fun toString(): String {
        return "Note{" +
                "name='" + name + '\'' +
                ", eighth=" + eighth +
                ", duration=" + duration +
                ", channel=" + channel +
                '}'
    }
}