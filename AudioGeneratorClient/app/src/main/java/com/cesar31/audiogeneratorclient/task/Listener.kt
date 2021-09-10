package com.cesar31.audiogeneratorclient.task

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cesar31.audiogeneratorclient.ResponseActivity
import com.cesar31.audiogeneratorclient.control.ResponseParserHandler
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Serializable
import java.net.ServerSocket

class Listener(var port: Int, var context: Context) : Thread(), Serializable {

    @SuppressLint("StaticFieldLeak")
    companion object {
        lateinit var listen: Listener
        var input: String = ""
        var response: String = ""
        fun setListener(l : Listener) {
            this.listen = l
        }

        fun getListener(): Listener {
            return this.listen
        }
    }

    override fun run() = try {
        val server = ServerSocket(port)
        while(true) {
            server.accept().use { socket ->
                val reader = InputStreamReader(socket.getInputStream())
                val br = BufferedReader(reader)
                val message = br.readText();
                // println("Recibiendo:\n$message")
                parseResponse(message)
            }
        }
    } catch(e: IOException) {
        e.printStackTrace(System.out)
    }

    private fun parseResponse(input: String) {
        response = input
        val parser = ResponseParserHandler()
        val bundle: Bundle = parser.parseResponse(input);
        getResponseActivity(bundle)
    }

    private fun getResponseActivity(bundle: Bundle) {
        val i = Intent(context, ResponseActivity::class.java)
        i.putExtras(bundle)
        context.startActivity(i)
    }
}