package com.example.cameratrapv2.screens.CameraIndFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.cameratrapv2.R
import com.example.cameratrapv2.activity.MainActivity
import kotlinx.android.synthetic.main.card_view_for_recycler.*
import kotlinx.android.synthetic.main.fragment_camera_ind.*


class CameraIndFragment : Fragment() {
    lateinit private var viewModel: IndFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_ind, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = view.findViewById<TextView>(R.id.number)
        val numberFromMain = arguments?.getString("number")!!
        number.text = numberFromMain
        viewModel = ViewModelProvider(this).get(IndFragmentViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        button_GPS.setOnClickListener {
            (activity as MainActivity).navController.navigate(R.id.action_cameraIndFragment_to_getPosFragment)
        }
    }
}