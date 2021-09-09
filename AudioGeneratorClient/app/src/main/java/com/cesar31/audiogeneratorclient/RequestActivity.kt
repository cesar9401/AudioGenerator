package com.cesar31.audiogeneratorclient

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.cesar31.audiogeneratorclient.task.MyTask


class RequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        val btnSend = findViewById<Button>(R.id.btnSend)
        val txtInput = findViewById<EditText>(R.id.txtInput)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnHome = findViewById<Button>(R.id.btnHome)

        btnHome.setOnClickListener {
            getHome()
        }

        btnClear.setOnClickListener {
            txtInput.setText("")
        }

        btnSend.setOnClickListener {
            val txt = txtInput.text.toString()
            send(txt)
        }
    }

    private fun getHome() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun send(txt: String) {
        val ip: String = "192.168.10.106";
        val port: Int = 8080;
        val task = MyTask(ip, port, txt);
        task.execute();
    }
}