package com.cesar31.audiogeneratorclient.task


import android.os.AsyncTask
import java.io.IOException
import java.io.PrintWriter
import java.net.Socket


class MyTask(private val ip: String, private val port: Int, private val message: String) : AsyncTask<Void?, Void?, Void?>() {
    // private var socket: Socket? = null
    // private var printWriter: PrintWriter? = null
    // private val ip = "192.168.10.106"
    // private val port = 8080;

    companion object {
        var ipServer = "192.168.10.106"
    }

    override fun doInBackground(vararg p0: Void?): Void? {
        try {
            // val thisIP = getIP()
            println("Sending: \n$message")
            val socket = Socket(ip, port)
            val printWriter = PrintWriter(socket.getOutputStream())
            printWriter.write(message)
            printWriter.flush()
            printWriter.close()
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace(System.out)
        }
        return null
    }
}
