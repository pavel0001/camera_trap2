package com.example.cameratrapv2.screens.CameraIndFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndFragmentViewModel(application: Application): AndroidViewModel(application) {


    lateinit var allData: LiveData<List<CameraData>>
    lateinit var repository: TotalRepository




    init {

        val cameraDao = TotalDatabase.getDatabase(application).dao()
        repository = TotalRepository(cameraDao)
        allData = repository.getCameraDataAll()
    }
    fun insert(data: CameraData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCameraData(data)
    }

}