package com.example.cameratrapv2.screens.CameraIndFragment

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.CommandData
import com.example.cameratrapv2.models.LogsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class IndFragmentViewModel(application: Application): AndroidViewModel(application) {


    private val repository: TotalRepository = TotalRepository(TotalDatabase.getDatabase(application).dao())
    val logsLiveData = repository.getLogsData()


    fun updateCameraInfo(number: String, signal: String, battery: String, storage: String) = viewModelScope.launch(Dispatchers.IO){
        lateinit var camera: CameraData
        Log.i("MyTag","updateCameraInfo")
        Log.i("MyTag","number $number  signal $signal battery $battery storage $storage")
        repository.getCameraNumbers().forEach {
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
    fun sendCmd(key: Int, number: String, param: String ="")= viewModelScope.launch(Dispatchers.IO) {

        var camera = repository.getCameraDataFromNumber(number)
        val cmd = repository.getCmdList().get(key)
        val df = SimpleDateFormat("yyyyMMddHHmmss", Locale.ROOT)
            if( ! cmd.valueOn.isNullOrEmpty()) {
                    var text: String? = ""
                        when (key) {
                        1 -> if (camera.pir_state) {
                            text = cmd.valueOff
                            camera.pir_state = false
                            repository.updateCameraData(camera)
                        }
                        else {
                            text = cmd.valueOn
                            camera.pir_state = true
                            repository.updateCameraData(camera)
                        }
                        3, 4 -> text = cmd.valueOn.replace("number", param, true)
                        6 -> text = cmd.valueOn.replace("time", df.format(Date()), ignoreCase = true)
                        7 -> if (camera.sms_state) {
                            text = cmd.valueOff
                            camera.sms_state = false
                            repository.updateCameraData(camera)
                        }
                        else {
                            text = cmd.valueOn
                            camera.sms_state = true
                            repository.updateCameraData(camera)
                        }
                        else -> text = cmd.valueOn
                    }
                repository.sendCmd(number, text)
                repository.insertLogsData(LogsData(number, text, Date().time))
                Log.i("MyTag", LogsData(number, text, Date().time).toString())
            }

    }

}