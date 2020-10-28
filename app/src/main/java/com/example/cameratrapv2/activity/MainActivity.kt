package com.example.cameratrapv2.activity

import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cameratrapv2.R
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.UriImgData
import com.example.cameratrapv2.receivers.SmsReceiver
import com.example.cameratrapv2.screens.MainFragment.MainFragmentViewModel
import com.example.cameratrapv2.utils.*


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        APP_ACTIVITY = this
        navController = Navigation.findNavController(this, R.id.nav_host)
        viewModel = ViewModelProvider(this).get( MainActivityViewModel::class.java)
        viewModel.initializeCMD()


        Log.i("MyTag", "MainActivity onCreate")
    }

    override fun onStart() {
        super.onStart()
        checkPermissions(READ_SMS)

    }

    override fun onResume() {
        super.onResume()
        Log.i("MyTag", "MainActivity onRes")
    }

    fun separateMessage(textBody: String, number: String){
        viewModel.separateMessage(textBody, number)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_SMS)
            == PackageManager.PERMISSION_GRANTED){
            val mainFragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
            MyToast.succes(this, "READ_SMS granted. Run loader")
            mainFragmentViewModel.runLoaderMmsData()
        }
    }
}