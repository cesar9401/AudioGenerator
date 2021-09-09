package com.cesar31.audiogeneratorclient.model

class Playlist
/**
 * Constructor para listado de listas
 * @param name nombre de la lista
 * @param count cantidad de pistas
 * @param random
 * @param circular
 */(var name: String, var count: Int, var isRandom: Boolean, var isCircular: Boolean) {
    var tracks: List<Track>? = null
    override fun toString(): String {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", random=" + isRandom +
                ", circular=" + isCircular +
                '}'
    }
}