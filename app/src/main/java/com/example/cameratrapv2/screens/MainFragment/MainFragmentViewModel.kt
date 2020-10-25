package com.example.cameratrapv2.screens.MainFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel(application: Application): AndroidViewModel(application) {

    val repository: TotalRepository = TotalRepository(TotalDatabase.getDatabase(application).dao())
    val allData: LiveData<List<CameraData>> = repository.getCameraDataAll()
    private lateinit var number_list: List<CameraData>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            number_list = repository.getCameraNumbers()
        }
    }

    fun insert(data: CameraData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCameraData(data)
    }
    fun delete(camera: CameraData)= viewModelScope.launch(Dispatchers.IO){
        repository.deleteCamera(camera)
    }
    fun getCameraList(): MutableList<CameraData>{
        return number_list.toMutableList()
    }

}