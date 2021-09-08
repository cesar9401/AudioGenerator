package com.cesar31.audiogeneratorclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPiano = findViewById<Button>(R.id.btnPiano)
        val btnRequest = findViewById<Button>(R.id.btnRequest)

    }
}
