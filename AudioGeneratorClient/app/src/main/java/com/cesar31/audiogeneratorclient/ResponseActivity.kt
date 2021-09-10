package com.cesar31.audiogeneratorclient

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

class ResponseActivity : AppCompatActivity() {

    private lateinit var frame: FrameLayout

    // fragments
    private lateinit var initFragment: Fragment
    private lateinit var responseFragment: Fragment

    private lateinit var bundle: Bundle

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_response)

        frame = findViewById<FrameLayout>(R.id.frameLayout)

        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnTable = findViewById<Button>(R.id.btnTable)
        val btnResponse = findViewById<Button>(R.id.btnResponse)

        // Inicializar fragments
        initFragment = InitFragment()
        responseFragment = ResponseFragment()

        btnBack.setOnClickListener {
            getEditor()
        }

        btnTable.setOnClickListener {
            // Acciones para mostrar la tabla
            initFragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, initFragment).commit();
        }

        btnResponse.setOnClickListener {
            // Acciones para mostrar la respuesta del servidor
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, responseFragment).commit();
        }

        // Recuperar datos del listener
        getDataFromListener()

        // Bundle para initFragment
        initFragment.arguments = bundle

        // Cambiarnos a initFragment
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, initFragment).commit();
    }

    private fun getEditor() {
        val i = Intent(this, RequestActivity::class.java)
        startActivity(i)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getDataFromListener() {
        val bundle: Bundle? = intent.getExtras()
        if(bundle != null) {
            this.bundle = bundle!!
        }
    }
}