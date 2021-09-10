package com.cesar31.audiogeneratorclient.model

import java.io.Serializable

class Track
/**
 * Constructor para listado de pistas
 * @param name nombre de la pista
 * @param duration duracion de la pista
 */(var name: String, var duration: Double): Serializable {
    var notes: List<Note>? = null
    override fun toString(): String {
        return "Track(name='$name', duration=$duration)"
    }

}