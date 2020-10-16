package com.example.cameratrapv2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.cameratrapv2.models.CameraData
import com.example.cameratrapv2.models.CommandData
import com.example.cameratrapv2.models.LogsData
import com.example.cameratrapv2.models.UriImgData

class TotalRepository(private val dao: TotalDao) {
    //######################Camera##############################
    fun getCameraDataAll(): LiveData<List<CameraData>> = dao.getCameraDataAll()

    suspend fun insertCameraData(camera: CameraData){
        dao.insertCameraData(camera)
    }
    suspend fun updateCameraData(camera: CameraData){
        dao.updateCameraData(camera)
    }
    suspend fun deleteCamera(camera: CameraData){
        dao.delete(camera)
    }
    fun getCameraNumbers(): List<CameraData> = dao.getCameraData()


    //######################URI##############################

    suspend fun updateUriImgData(data: UriImgData){
        dao.updateUriImgData(data)
    }
    suspend fun deleteOldUriImg(date: Long){
        dao.deleteOldUriImg(date)
    }
    fun getUriImg(): LiveData<List<UriImgData>> = dao.getUriImg()

    suspend fun insertUriImgData(data: UriImgData){
        dao.insertUriImgData(data)
    }
    //######################LOGS##############################

    fun getLogsData(): LiveData<List<LogsData>> = dao.getLogsData()
    suspend fun insertLogsData(data: LogsData){
        dao.insertLogsData(data)
    }
    suspend fun updateLogsData(data: LogsData){
        dao.updateLogsData(data)
    }
    suspend fun deleteLogsDataFromNumber(num: String){
        dao.deleteLogsDataFromNumber(num)
    }
    //######################CMD##############################

    fun getCmd(): LiveData<List<CommandData>> = dao.getCmd()

    suspend fun updateCmdData(cmd: CommandData){
        dao.updateCmdData(cmd)
    }
}