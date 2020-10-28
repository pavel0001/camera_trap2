package com.example.cameratrapv2.screens.MainFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cameratrapv2.database.MmsLoader.LoaderRepository
import com.example.cameratrapv2.database.TotalDatabase
import com.example.cameratrapv2.database.TotalRepository
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.UriImgData
import com.example.cameratrapv2.utils.READ_SMS
import com.example.cameratrapv2.utils.checkPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainFragmentViewModel(application: Application): AndroidViewModel(application) {

    private val repository: TotalRepository = TotalRepository(TotalDatabase.getDatabase(application).dao())
    val allData: LiveData<List<CameraData>> = repository.getCameraDataAll()

    private lateinit var loaderRepository: LoaderRepository

    var camera_list: List<CameraData> = mutableListOf<CameraData>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            camera_list = repository.getCameraNumbers()
            val number_list = mutableListOf<String>()
            camera_list.forEach { number_list.add(it.number) }
            loaderRepository = LoaderRepository(application, number_list)

        }
    }


    fun runLoaderMmsData() = viewModelScope.launch(Dispatchers.IO){
        if(camera_list.isNotEmpty()) {
            val start = Date()
            Log.i("MyTag", "Loader is running! time ${start.toString()}")
            val idList = repository.getIdListFromUriData()
            Log.i("MyTag", "In base already exist ${idList.size} id ")
            val uriData = loaderRepository.getAllMmsData(idList)
            insertUriImgDataFromList(uriData)
            val different = Date(Date().time - start.time)
            Log.i("MyTag", "Loader is finishes! time ${different.toString()}")
            updateCameraInfoAboutLastImage()
        }
    }

    fun insert(data: CameraData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertCameraData(data)
    }
    fun delete(camera: CameraData)= viewModelScope.launch(Dispatchers.IO){
        repository.deleteCamera(camera)
    }
    fun getCameraList(): MutableList<CameraData>{
        return camera_list.toMutableList()
    }

    fun updateCameraInfoAboutLastImage() = viewModelScope.launch(Dispatchers.IO){
        camera_list.forEach {
           val uriData =  repository.getLastUriDataFromNumber(it.number)
            if(uriData != null) {
                it.last_photo = uriData.date_stamp
                it.last_photo_uri = uriData.uri_img
                it.total_images = repository.getHowMuchImagesHasCamera(it.number)
            }
        }
        repository.updateCameraDataFromList(camera_list)
    }
    //#################################################################################################
    //###############################URI###############################################################
    //#################################################################################################
    fun insertUriImgDataFromList(data: List<UriImgData>) = viewModelScope.launch(Dispatchers.IO){
        repository.insertUriImgDataFromList(data)
    }


}