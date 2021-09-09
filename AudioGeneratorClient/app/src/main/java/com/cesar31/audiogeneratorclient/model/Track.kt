package com.cesar31.audiogeneratorclient.model

class Track
/**
 * Constructor para listado de pistas
 * @param name nombre de la pista
 * @param duration duracion de la pista
 */(var name: String, var duration: Double) {
    var notes: List<Note>? = null
    override fun toString(): String {
        return "Track(name='$name', duration=$duration)"
    }

}