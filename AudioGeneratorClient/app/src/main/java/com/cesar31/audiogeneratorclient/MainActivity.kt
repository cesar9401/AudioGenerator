package com.cesar31.audiogeneratorclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.cesar31.audiogeneratorclient.task.Listener
import org.jfugue.player.Player
import java.net.Inet4Address
import java.net.NetworkInterface

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPiano = findViewById<Button>(R.id.btnPiano)
        val btnRequest = findViewById<Button>(R.id.btnRequest)
        val ipText = findViewById<TextView>(R.id.ip)

        val port = 8081
        val listener = Listener(port, this)
        Listener.setListener(listener)
        listener.start()

        btnRequest.setOnClickListener{
            getEditor(listener)
        }

        btnPiano.setOnClickListener {
            // write your code here
        }

        ipText.text = getIP()
    }

    private fun getEditor(listener: Listener) {
        val i = Intent(this, RequestActivity::class.java)
        //val bundle = Bundle()

        //bundle.putSerializable("listener", listener)
        //i.putExtras(bundle)
        startActivity(i)
    }

    private fun getIP():String {
        NetworkInterface.getNetworkInterfaces()?.toList()?.map { newtworkInterface ->
            newtworkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let {
                return it.hostAddress
            }
        }

        return ""
    }
}
