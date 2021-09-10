package com.cesar31.audiogeneratorclient.control

import android.os.Bundle
import com.cesar31.audiogeneratorclient.parser.ClientLex
import com.cesar31.audiogeneratorclient.parser.ClientParser
import java.io.Serializable
import java.io.StringReader
import java.lang.Exception

class ResponseParserHandler {
    fun parseResponse(input: String?): Bundle {
        val bundle = Bundle()
        val lex = ClientLex(StringReader(input))
        val parser = ClientParser(lex)
        try {
            parser.parse()
            when {
                parser.errors != null -> {
                    // Errores
                    val errors = parser.errors
                    bundle.putSerializable("errors", errors as Serializable)
                    return bundle
                }
                parser.playlists != null -> {
                    // listado de listas
                    val playlists = parser.playlists
                    bundle.putSerializable("playlists", playlists as Serializable)
                    return bundle
                }
                parser.tracks != null -> {
                    // listado de pistas
                    val tracks = parser.tracks
                    bundle.putSerializable("tracks", tracks as Serializable)
                    return bundle
                }
                parser.playlist != null -> {
                    // playlist especifica con listado de pistas
                    val play = parser.playlist
                    bundle.putSerializable("play", play)
                    return bundle
                }
                parser.track != null -> {
                    // pista con sus respectivas notas
                    val track = parser.track
                    bundle.putSerializable("track", track)
                    return bundle
                }
            }
        } catch (e: Exception) {
            e.printStackTrace(System.out)
        }
        return bundle
    }
}