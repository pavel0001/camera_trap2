package com.example.cameratrapv2.activity

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cameratrapv2.R
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.receivers.SmsReceiver
import com.example.cameratrapv2.utils.APP_ACTIVITY


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
        viewModel = ViewModelProvider(this).get( MainActivityViewModel::class.java)
        viewModel.initializeCMD()
        Log.i("MyTag", "MainActivity onCreate")
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this
    }

    override fun onResume() {
        super.onResume()
        Log.i("MyTag", "MainActivity onRes")
    }

    fun separateMessage(textBody: String, number: String){
        viewModel.separateMessage(textBody, number)
    }
}