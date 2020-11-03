package com.example.cameratrapv2.screens.CameraIndFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.LogsData
import com.example.cameratrapv2.models.UriImgData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class IndFragmentViewModel(application: Application): AndroidViewModel(application) {


    private val repository: TotalRepository = TotalRepository(TotalDatabase.getDatabase(application).dao())
    lateinit var cameraList: List<CameraData>
    val logsLiveData = repository.getLogsData()

    init {
        viewModelScope.launch(Dispatchers.IO){
            cameraList = repository.getCameraDataInList()
        }
    }

    fun getUriImgLiveData(): LiveData<List<UriImgData>> = repository.getUriImgLiveData()

    fun getCameraDataFromNumber(number: String): CameraData?{
        var cameraData: CameraData? = null
        cameraList.forEach {
            if(it.number.equals(number))
                cameraData = it
        }
        return cameraData
    }

    fun checkIfCameraHasCoord(number: String): Boolean {
        val camera: CameraData? = getCameraDataFromNumber(number)
            if (camera != null) {
                return camera.latitude != 0.0 && camera.longitude != 0.0
            }
        return false
        }

    fun sendCmd(key: Int, number: String, param: String ="")= viewModelScope.launch(Dispatchers.IO) {

        val camera = getCameraDataFromNumber(number)
        if (camera != null) {
            val cmd = repository.getCmdList().get(key)
            val df = SimpleDateFormat("yyyyMMddHHmmss", Locale.ROOT)
            if (!cmd.valueOn.isEmpty()) {
                var text: String? = ""
                when (key) {
                    1 -> if (camera.pir_state) {
                        text = cmd.valueOff
                        camera.pir_state = false
                        repository.updateCameraData(camera)
                    } else {
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
                    } else {
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

}


