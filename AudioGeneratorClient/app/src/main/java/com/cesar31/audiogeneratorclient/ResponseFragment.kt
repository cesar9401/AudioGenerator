package com.cesar31.audiogeneratorclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.cesar31.audiogeneratorclient.task.Listener

class ResponseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_response, container, false)
        val txtResponse = view.findViewById<EditText>(R.id.txtResponse)
        txtResponse.setText(Listener.response)
        return view
    }
}