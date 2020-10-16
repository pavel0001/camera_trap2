package com.example.cameratrapv2.screens.CameraAddFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraAddViewModel(application: Application): AndroidViewModel(application) {
    lateinit var repository: TotalRepository
    private lateinit var number_list: List<CameraData>



    init {

        val cameraDao = TotalDatabase.getDatabase(application).dao()
        repository = TotalRepository(cameraDao)
        viewModelScope.launch(Dispatchers.IO) {
            number_list = repository.getCameraNumbers()
        }
    }

    fun insert(data: CameraData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCameraData(data)
    }

    fun isThisInBase(number: String?):Boolean{
        val tmp = ArrayList<String>()
        number_list.forEach {
            tmp.add(it.number)
        }
        if(tmp.contains(number)){
            return true
        }
        return false
    }
}