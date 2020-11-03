package com.example.cameratrapv2.activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.regex.Pattern

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    val repository = TotalRepository(TotalDatabase.getDatabase(application).dao())



        fun initializeCMD() = viewModelScope.launch(Dispatchers.IO) {
        if(repository.getCmdList().isNullOrEmpty()) repository.initializedCmd(viewModelScope)
        }

        fun isThisOurCamera(number: String):Boolean {
            repository.getCameraDataInList().forEach {
                if (it.number.equals(number)) {
                    return true
                }
            }
            return false
        }

        fun updateCameraInfo(number: String, signal: String, battery: String, storage: String) = viewModelScope.launch(Dispatchers.IO){
            lateinit var camera: CameraData
            Log.i("MyTag","updateCameraInfo")
            Log.i("MyTag","number $number  signal $signal battery $battery storage $storage")
            repository.getCameraDataInList().forEach {
                    if(it.number.equals(number)){
                        camera = it
                    }
                }
            camera.battery = battery
            camera.signal = signal
            camera.storage = storage
            camera.last_update = Date().time
            repository.updateCameraData(camera)
        }

    fun separateMessage(textBody: String, input_number: String)  = viewModelScope.launch(Dispatchers.IO) {
        val number = input_number.replace("+","", true)
        if (isThisOurCamera(number)) {  // check if this number already exist in our camera's database
            Log.i("MyTag", "separateMessage")
            val pattern = Pattern.compile(
                "(signal|battery):(\\d+)|(\\d{4,})M.+?(\\d+)M",
                Pattern.CASE_INSENSITIVE
            )
            val matcher = pattern.matcher(textBody)
            var signal: String = ""
            var battery: String = ""
            var storage: String = ""

            while (matcher.find()) {
                if (matcher.group(1) != null && matcher.group(1).equals("signal", true)) {
                    signal = matcher.group(2)!!
                } else if (matcher.group(1) != null && matcher.group(1).equals("battery", true)) {
                    battery = matcher.group(2)!!
                } else if (matcher.group(3) != null && matcher.group(4) != null) {
                    val a = matcher.group(3)!!.toDouble()
                    val b = matcher.group(4)!!.toDouble()
                    storage = if (a > b) ((b / a) * 100).toInt().toString()
                    else ((a / b) * 100).toInt().toString()
                }
            }
            if (!(storage.isEmpty() && signal.isEmpty() && battery.isEmpty())) { // chech what variables isn't empty
                updateCameraInfo(number, signal, battery, storage) // update camera
            }
        }
        else {
            Log.i("MyTag", "This number don't exist in our db")
        }
    }






}