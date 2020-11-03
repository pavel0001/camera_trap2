package com.example.cameratrapv2.screens.GetPosFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetPosFragmentViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TotalRepository = TotalRepository(TotalDatabase.getDatabase(application).dao())

    fun getCameraLiveData(): LiveData<List<CameraData>> = repository.getCameraDataAll()

    fun updateCameraData(camera: CameraData) = viewModelScope.launch(Dispatchers.IO){
        repository.updateCameraData(camera)
    }


}