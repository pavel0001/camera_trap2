package com.example.cameratrapv2.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    lateinit private var repository: TotalRepository

    lateinit var allCameraData: LiveData<List<CameraData>>

    init{
        val dao = TotalDatabase
            .getDatabase(application)
            .dao()
        repository = TotalRepository(dao)
        allCameraData = repository.getCameraDataAll()
    }

    fun insert(data: CameraData) = viewModelScope.launch(Dispatchers.IO){
        repository.insertCameraData(data)
    }
    fun update(data: CameraData) = viewModelScope.launch(Dispatchers.IO){
        repository.updateCameraData(data)
    }
    fun delete(data: CameraData) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteCamera(data)
    }





}