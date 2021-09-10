package com.cesar31.audiogeneratorclient

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.cesar31.audiogeneratorclient.control.Table
import com.cesar31.audiogeneratorclient.model.Err
import com.cesar31.audiogeneratorclient.model.Note
import com.cesar31.audiogeneratorclient.model.Playlist
import com.cesar31.audiogeneratorclient.model.Track

class InitFragment : Fragment() {
    private var tableLayout: TableLayout? = null
    private var list: ArrayList<Err>? = null
    private var playlists: ArrayList<Playlist>? = null
    private var listTrack: ArrayList<Track>? = null
    private var play: Playlist? = null
    private var track: Track? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            when {
                it.getSerializable("errors") != null -> {
                    list = it.getSerializable("errors") as ArrayList<Err>
                }
                it.getSerializable("playlists") != null -> {
                    playlists = it.getSerializable("playlists") as ArrayList<Playlist>
                }
                it.getSerializable("tracks") != null -> {
                    listTrack = it.getSerializable("tracks") as ArrayList<Track>
                }
                it.getSerializable("play") != null -> {
                    play = it.getSerializable("play") as Playlist
                }
                it.getSerializable("track") != null -> {
                    track = it.getSerializable("track") as Track
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_init, container, false)
        tableLayout = view.findViewById<TableLayout>(R.id.table)
        val text = view.findViewById<TextView>(R.id.subtitle)

        list?.let { setTableErrors(it); text.setText("Errores") }
        playlists?.let { setTablePlaylists(it); text.setText("Listas de Reproduccion") }
        listTrack?.let { setTableTracks(it); text.setText("Biblioteca General") }
        play?.let {
            // Mostrar informacion de la lista
            val s = "Nombre lista: ${it.name} Pistas: ${it.count} Circular: ${if(it.isCircular) "si" else "no"} Random: ${if(it.isRandom) "si" else "no"}"
            text.text = s
            if(it.tracks != null) setTableTracks(it.tracks as ArrayList<Track>)
        }
        track?.let {
            // Mostrar informacion de la pista
            val s = "Nombre pista: ${it.name} Duracion: ${it.duration/1000}"
            text.text = s
            if(it.notes != null) setTableTrack(it.notes as ArrayList<Note>)
        }

        return view;
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setTableErrors(listE: ArrayList<Err>) {
        val table = Table(this.context as Activity, tableLayout)
        table.addHeader(R.array.header_errors)
        for (e in listE) {
            val l = ArrayList<String>()
            l.add(e.type)
            l.add(e.line.toString())
            l.add(e.column.toString())
            l.add(e.lexeme)
            l.add(e.description)
            table.addRowTable(l)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setTablePlaylists(playlists: ArrayList<Playlist>) {
        val table = Table(this.context as Activity, tableLayout)
        table.addHeader(R.array.header_playlists)
        for (p in playlists) {
            val l = ArrayList<String>()
            l.add(p.name)
            l.add(p.count.toString())
            l.add(if (p.isCircular) "Si" else "No")
            l.add(if (p.isRandom) "Si" else "No")
            table.addRowTable(l)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setTableTracks(tracks: ArrayList<Track>) {
        val table = Table(this.context as Activity, tableLayout)
        table.addHeader(R.array.header_tracks)
        for(t in tracks) {
            val l = ArrayList<String>()
            l.add(t.name)
            l.add((t.duration/1000).toString())
            table.addRowTable(l)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setTableTrack(notes: ArrayList<Note>) {
        val table = Table(this.context as Activity, tableLayout)
        table.addHeader(R.array.header_notes)
        for(n in notes) {
            val l = ArrayList<String>()
            l.add(if (n.name.equals("R")) "esperar" else n.name)
            l.add(n.eighth)
            l.add(n.duration.toString())
            l.add(n.channel.toString())
            table.addRowTable(l)
        }
    }
}