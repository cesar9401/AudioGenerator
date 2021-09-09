package com.cesar31.audiogeneratorclient.model

class Note(var name: String, var eighth: String, var duration: Double) {
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