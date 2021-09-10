package com.cesar31.audiogeneratorclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cesar31.audiogeneratorclient.task.Listener
import org.jfugue.player.Player

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPiano = findViewById<Button>(R.id.btnPiano)
        val btnRequest = findViewById<Button>(R.id.btnRequest)

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
    }

    private fun getEditor(listener: Listener) {
        val i = Intent(this, RequestActivity::class.java)
        //val bundle = Bundle()

        //bundle.putSerializable("listener", listener)
        //i.putExtras(bundle)
        startActivity(i)
    }
}
