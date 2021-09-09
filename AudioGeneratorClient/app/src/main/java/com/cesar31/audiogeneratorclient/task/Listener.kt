package com.cesar31.audiogeneratorclient.task

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket

class Listener(private val port: Int) : Thread() {
    override fun run() {
        try {
            val server = ServerSocket(port)
            while(true) {
                server.accept().use { socket ->
                    val reader = InputStreamReader(socket.getInputStream())
                    val br = BufferedReader(reader)
                    val message = br.readText();
                    println("Recibiendo:\n$message")
                }
            }
        } catch(e: IOException) {
            e.printStackTrace(System.out)
        }
    }
}